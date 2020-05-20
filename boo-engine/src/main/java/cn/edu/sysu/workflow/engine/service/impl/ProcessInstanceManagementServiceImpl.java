package cn.edu.sysu.workflow.engine.service.impl;

import cn.edu.sysu.workflow.common.entity.*;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.common.util.TimestampUtil;
import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.engine.core.BOXMLExecutor;
import cn.edu.sysu.workflow.engine.core.Context;
import cn.edu.sysu.workflow.engine.core.Evaluator;
import cn.edu.sysu.workflow.engine.core.EvaluatorFactory;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.env.SimpleErrorReporter;
import cn.edu.sysu.workflow.engine.core.instanceTree.InstanceManager;
import cn.edu.sysu.workflow.engine.core.instanceTree.RInstanceTree;
import cn.edu.sysu.workflow.engine.core.instanceTree.RTreeNode;
import cn.edu.sysu.workflow.engine.core.io.BOXMLReader;
import cn.edu.sysu.workflow.engine.core.model.EnterableState;
import cn.edu.sysu.workflow.engine.core.model.SCXML;
import cn.edu.sysu.workflow.engine.core.model.extend.Task;
import cn.edu.sysu.workflow.engine.core.model.extend.Tasks;
import cn.edu.sysu.workflow.engine.dao.*;
import cn.edu.sysu.workflow.engine.service.ProcessInstanceManagementService;
import cn.edu.sysu.workflow.engine.util.SerializationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * {@link ProcessInstanceManagementService}
 *
 * @author Ariana, Rinkako, Skye
 * Created on 2019/12/31
 */
@Service
public class ProcessInstanceManagementServiceImpl implements ProcessInstanceManagementService {

    private static final Logger log = LoggerFactory.getLogger(ProcessInstanceManagementServiceImpl.class);

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    @Autowired
    private BusinessProcessDAO businessProcessDAO;

    @Autowired
    private BusinessObjectDAO businessObjectDAO;

    @Autowired
    private TaskItemDAO taskItemDAO;

    @Autowired
    private ArchivedTreeDAO archivedTreeDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void launchProcess(String processInstanceId, String accountId) {
        boolean cmtFlag = false;
        try {
            ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
            if (processInstance == null) {
                throw new RuntimeException("ProcessInstance:" + processInstanceId + " is null!");
            }
            processInstance.setLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
            processInstance.setLaunchAccountId(accountId);
            processInstance.setEngineId(BooEngineApplication.ENGINE_ID);
            processInstanceDAO.update(processInstance);

            String processId = processInstance.getProcessId();
            BusinessProcess businessProcess = businessProcessDAO.findOne(processId);
            if (businessProcess == null) {
                throw new RuntimeException("BusinessProcess:" + processId + " is NULL!");
            }
            // TODO 可能出现重复写错误，需要加锁
            businessProcess.setLaunchCount(businessProcess.getLaunchCount() + 1);
            businessProcess.setLastLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
            businessProcessDAO.update(businessProcess);

            String mainBusinessObjectName = businessProcess.getMainBusinessObjectName();
            List<BusinessObject> boList = businessObjectDAO.findBusinessObjectsByProcessId(processId);
            BusinessObject mainBusinessObject = null;
            for (BusinessObject bo : boList) {
                if (bo.getBusinessObjectName().equals(mainBusinessObjectName)) {
                    mainBusinessObject = bo;
                    break;
                }
            }
            cmtFlag = true;
            if (mainBusinessObject == null) {
                log.error("[" + processInstanceId + "]Main BO not exist for launching process: " + processInstanceId);
                return;
            }
            byte[] serializedBO = mainBusinessObject.getSerialization();
            SCXML deserializedBO = SerializationUtil.DeserializationSCXMLByByteArray(serializedBO);
            this.executeBO(deserializedBO, processInstanceId, processId);
        } catch (Exception ex) {
            if (!cmtFlag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            log.error("[" + processInstanceId + "]Start process but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("[" + processInstanceId + "]Start process but exception occurred, service rollback", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AbstractMap.SimpleEntry<String, List<String>> uploadBusinessObject(String businessProcessId, String businessObjectName, String content) {
        boolean cmtFlag = false;
        try {
            // 新建BO
            String businessObjectId = BusinessObject.PREFIX + IdUtil.nextId();
            BusinessObject businessObject = new BusinessObject();
            businessObject.setBusinessObjectId(businessObjectId);
            businessObject.setProcessId(businessProcessId);
            businessObject.setBusinessObjectName(businessObjectName);
            businessObject.setContent(content);
            businessObject.setStatus(0);
            businessObjectDAO.save(businessObject);
            cmtFlag = true;

            // 解析BO
            List<String> businessRoles = new ArrayList<>();
            SCXML scxml = this.parseStringToSCXML(content);
            if (scxml != null) {
                HashSet<String> oneInvolves = this.getInvolvedBusinessRole(scxml);
                businessRoles.addAll(oneInvolves);
                businessObject.setBusinessRoles(JsonUtil.jsonSerialization(oneInvolves, ""));
                businessObject.setSerialization(SerializationUtil.SerializationSCXMLToByteArray(scxml));
                Tasks tasks = scxml.getTasks();
                for (Task t : tasks.getTaskList()) {
                    AbstractMap.SimpleEntry<String, String> heDesc = t.GenerateCallbackDescriptor();
                    TaskItem taskItem = new TaskItem();
                    taskItem.setTaskItemId(TaskItem.PREFIX + IdUtil.nextId());
                    taskItem.setBusinessObjectId(businessObjectId);
                    taskItem.setTaskPolymorphismId(t.getId());
                    taskItem.setTaskPolymorphismName(t.getName());
                    taskItem.setBusinessRole(t.getBrole());
                    taskItem.setPrinciple(t.getPrinciple().GenerateDescriptor());
                    taskItem.setEventDescriptor(heDesc.getValue());
                    taskItem.setHookDescriptor(heDesc.getKey());
                    taskItem.setDocumentation(t.getDocumentation());
                    taskItem.setParameters(t.GenerateParamDescriptor());
                    taskItemDAO.save(taskItem);
                }
                businessObjectDAO.update(businessObject);
            }
            return new AbstractMap.SimpleEntry<>(businessObjectId, businessRoles);

        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (cmtFlag) {
                log.error(String.format("When serialize BOList(%s), exception occurred.", businessObjectName));
            } else {
                log.error("Error in creating a new bo");
            }
            log.error("Upload BO but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("Upload BO but exception occurred, service rollback", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getSpanTreeDescriptor(String processInstanceId) {
        RInstanceTree tree = InstanceManager.getInstanceTree(processInstanceId, false);
        if (tree == null || tree.Root == null) {
            ArchivedTree archivedTree = null;
            try {
                archivedTree = archivedTreeDAO.findOne(processInstanceId);
            } catch (Exception ex) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            if (archivedTree == null) {
                return null;
            }
            return archivedTree.getTree();
        }
        FriendlyTreeNode rootFNode = new FriendlyTreeNode();
        this.Nephren(tree.Root, rootFNode);
        return JsonUtil.jsonSerialization(rootFNode, processInstanceId);
    }

    @Override
    public void executeBO(SCXML scxml, String processInstanceId, String processId) {
        try {
            Evaluator evaluator = EvaluatorFactory.getEvaluator(scxml);
            BOXMLExecutor executor = new BOXMLExecutor(evaluator, new MultiStateMachineDispatcher(), new SimpleErrorReporter());
            Context rootContext = evaluator.newContext(null);
            executor.setRootContext(rootContext);
            executor.setProcessInstanceId(processInstanceId);
            executor.setProcessId(processId);
            executor.setStateMachine(scxml);
            executor.go();
        } catch (Exception e) {
            log.error("[" + processInstanceId +"]When ExecuteBO, exception occurred, " + e.toString());
            throw new ServiceFailureException("When ExecuteBO, exception occurred", e);
        }
    }

    /**
     * Interpret XML string to SCXML instance.
     *
     * @param boXMLContent BO XML string
     * @return {@code SCXML} instance
     */
    private SCXML parseStringToSCXML(String boXMLContent) {
        try {
            InputStream inputStream = new ByteArrayInputStream(boXMLContent.getBytes());
            return BOXMLReader.read(inputStream);
        } catch (Exception e) {
            log.error(String.format("When read BO XML data(%s), exception occurred, %s", boXMLContent, e));
        }
        return null;
    }

    /**
     * Get involved business role name of one BO.
     *
     * @param scxml BO {@code SCXML} instance.
     * @return HashSet of involved business role name
     */
    private HashSet<String> getInvolvedBusinessRole(SCXML scxml) {
        HashSet<String> retSet = new HashSet();
        ArrayList<Task> taskList = scxml.getTasks().getTaskList();
        for (Task task : taskList) {
            retSet.add(task.getBrole());
        }
        return retSet;
    }

    /**
     * Recursively handle span the user-friendly package tree of a specific instance tree.
     * This method is to commemorate a girl devoted her love to guard the happiness of who she loved and his lover. -RK
     *
     * @param node  current span root node
     * @param fNode user-friendly package node of current span node
     */
    private void Nephren(@NotNull RTreeNode node, @NotNull FriendlyTreeNode fNode) {
        fNode.BOName = node.getExect().getScInstance().getStateMachine().getName();
        fNode.GlobalId = node.getGlobalId();
        fNode.NotifiableId = node.getExect().NotifiableId;
        Set<EnterableState> status = node.getExect().getScInstance().getCurrentStatus().getActiveStates();
        HashSet<String> stringSet = new HashSet<>();
        for (EnterableState st : status) {
            stringSet.add(st.getId());
        }
        fNode.StatusDescriptor = JsonUtil.jsonSerialization(stringSet, node.getExect().processInstanceId);
        for (RTreeNode sub : node.Children) {
            FriendlyTreeNode subFn = new FriendlyTreeNode();
            this.Nephren(sub, subFn);
            fNode.Children.add(subFn);
        }
    }

    /**
     * A class for user-friendly tree node data package.
     */
    private class FriendlyTreeNode {

        public String NotifiableId;

        public String GlobalId;

        public String BOName;

        public String StatusDescriptor;

        public ArrayList<FriendlyTreeNode> Children = new ArrayList<>();
    }

}
