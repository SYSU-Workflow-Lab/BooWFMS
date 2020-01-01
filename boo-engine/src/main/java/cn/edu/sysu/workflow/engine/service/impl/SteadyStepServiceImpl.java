package cn.edu.sysu.workflow.engine.service.impl;

import cn.edu.sysu.workflow.common.entity.ArchivedTree;
import cn.edu.sysu.workflow.common.entity.BinStep;
import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.engine.core.*;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.env.SimpleErrorReporter;
import cn.edu.sysu.workflow.engine.core.instanceTree.InstanceManager;
import cn.edu.sysu.workflow.engine.core.instanceTree.RInstanceTree;
import cn.edu.sysu.workflow.engine.core.instanceTree.RTreeNode;
import cn.edu.sysu.workflow.engine.dao.ArchivedTreeDAO;
import cn.edu.sysu.workflow.engine.dao.BinStepDAO;
import cn.edu.sysu.workflow.engine.dao.ProcessInstanceDAO;
import cn.edu.sysu.workflow.engine.service.ProcessInstanceManagementService;
import cn.edu.sysu.workflow.engine.service.SteadyStepService;
import cn.edu.sysu.workflow.engine.util.SerializationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * {@link cn.edu.sysu.workflow.engine.service.SteadyStepService}
 *
 * @author Skye
 * Created on 2019/12/31
 */
@Service
public class SteadyStepServiceImpl implements SteadyStepService {

    private static final Logger log = LoggerFactory.getLogger(SteadyStepService.class);

    @Autowired
    private ProcessInstanceManagementService processInstanceManagementService;

    @Autowired
    private BinStepDAO binStepDAO;

    @Autowired
    private ArchivedTreeDAO archivedTreeDAO;

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    private boolean enableSteadyStep = true;

    /**
     * Write a entity step to entity memory.
     *
     * @param exctx BOXML execution context
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void writeSteady(BOXMLExecutionContext exctx) {
        if (!this.enableSteadyStep) {
            return;
        }
        try {
            BinStep binStep = binStepDAO.findOne(exctx.NodeId);
            if (binStep == null) {
                binStep = new BinStep();
                binStep.setBinStepId(exctx.NodeId);
                binStep.setProcessInstanceId(exctx.processInstanceId);
                binStep.setNotificationId(exctx.NotifiableId);
                RInstanceTree tree = InstanceManager.getInstanceTree(exctx.processInstanceId);
                RTreeNode parentNode = tree.GetNodeById(exctx.NodeId).Parent;
                binStep.setParentId(parentNode != null ? parentNode.getExect().NodeId : "");
                BOInstance boInstance = exctx.getSCXMLExecutor().detachInstance();
                binStep.setBinlog(SerializationUtil.SerializationBOInstanceToByteArray(boInstance));
                exctx.getSCXMLExecutor().attachInstance(boInstance);
                binStepDAO.save(binStep);
            } else {
                BOInstance boInstance = exctx.getSCXMLExecutor().detachInstance();
                binStep.setBinlog(SerializationUtil.SerializationBOInstanceToByteArray(boInstance));
                exctx.getSCXMLExecutor().attachInstance(boInstance);
                binStepDAO.update(binStep);
            }

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + exctx.processInstanceId + "]Write service entity step to DB failed, save action rollback.");
        }
    }

    /**
     * Clear entity step snapshot after final state, and write a span tree descriptor to archived tree table.
     *
     * @param processInstanceId process runtime record id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearSteadyWriteArchivedTree(String processInstanceId) {
        try {
            binStepDAO.deleteByProcessInstanceId(processInstanceId);
            ArchivedTree archivedTree = new ArchivedTree();
            archivedTree.setProcessInstanceId(processInstanceId);
            archivedTree.setTree(JsonUtil.jsonSerialization(processInstanceManagementService.getSpanTreeDescriptor(processInstanceId), processInstanceId));
            archivedTreeDAO.save(archivedTree);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + processInstanceId + "]Clear service entity step failed, action rollback.");
        }
    }

    /**
     * Resume instances from entity memory, and register it to instance manager.
     *
     * @param rtidList rtid in JSON list
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public List<String> resumeSteadyMany(String rtidList) {
        List<String> rtidItems = JsonUtil.jsonDeserialization(rtidList, List.class);
        List<String> failedList = new ArrayList<>();
        for (String rtid : rtidItems) {
            if (!this.resumeSteady(rtid)) {
                failedList.add(rtid);
            }
        }
        return failedList;
    }

    /**
     * Resume a instance from entity memory, and register it to instance manager.
     *
     * @param processInstanceId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resumeSteady(String processInstanceId) {
        boolean cmtFlag = false;
        try {
            // update runtime record
            List<BinStep> stepItems = binStepDAO.findBinStepsByProcessInstanceId(processInstanceId);
            ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
            if (processInstance != null) {
                processInstance.setEngineId(BooEngineApplication.ENGINE_ID);
                processInstanceDAO.update(processInstance);
            }
            cmtFlag = true;
            // find root node
            BinStep rootStep = stepItems.stream().filter(t -> StringUtils.isEmpty(t.getParentId())).findFirst().get();
            String rootNodeId = rootStep.getBinStepId();
            // recovery other node
            Stack<BinStep> workStack = new Stack<>();
            workStack.push(rootStep);
            while (!workStack.isEmpty()) {
                BinStep currentStep = workStack.pop();
                String currentNodeId = currentStep.getBinStepId();
                BOInstance curBin = SerializationUtil.DeserializationBOInstanceByByteArray(currentStep.getBinlog());
                Evaluator curEvaluator = EvaluatorFactory.getEvaluator(curBin.getStateMachine());
                BOXMLExecutor curExecutor = new BOXMLExecutor(curEvaluator, new MultiStateMachineDispatcher(), new SimpleErrorReporter());
                curExecutor.NodeId = curExecutor.getExctx().NodeId = currentNodeId;
                curExecutor.RootNodeId = rootNodeId;
                curExecutor.setRootContext(curEvaluator.newContext(null));
                curExecutor.setProcessInstanceId(processInstanceId);
                if (processInstance != null) {
                    curExecutor.setProcessId(processInstance.getProcessId());
                }
                curExecutor.attachInstance(curBin);
                curExecutor.setNotifiableId(currentStep.getNotificationId());
                curExecutor.resume(currentStep.getParentId());
                List<BinStep> currentChildren = stepItems.stream().filter(t -> t.getParentId().equals(currentNodeId)).collect(Collectors.toList());
                for (BinStep cc : currentChildren) {
                    workStack.push(cc);
                }
            }
            return true;
        } catch (Exception ex) {
            if (!cmtFlag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            log.error("[" + processInstanceId + "]Resume service entity step from DB failed, action rollback.");
            return false;
        }
    }

}
