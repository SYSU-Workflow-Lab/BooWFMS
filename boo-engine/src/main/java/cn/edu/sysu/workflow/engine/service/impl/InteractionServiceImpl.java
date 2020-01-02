package cn.edu.sysu.workflow.engine.service.impl;

import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.engine.core.BOXMLExecutionContext;
import cn.edu.sysu.workflow.engine.core.BOXMLIOProcessor;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.instanceTree.InstanceManager;
import cn.edu.sysu.workflow.engine.core.instanceTree.RInstanceTree;
import cn.edu.sysu.workflow.engine.core.instanceTree.RTreeNode;
import cn.edu.sysu.workflow.engine.core.model.extend.MessageMode;
import cn.edu.sysu.workflow.engine.service.InteractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * {@link InteractionService}
 *
 * @author Rinkako, Skye
 * Created on 2020/1/2
 */
@Service
public class InteractionServiceImpl implements InteractionService {

    private static final Logger log = LoggerFactory.getLogger(InteractionServiceImpl.class);

    @Override
    public void dispatchCallbackByProcessInstanceId(String processInstanceId, String bo, String on, String event, String payload) {
        try {
            HashMap payloadMap = payload != null ? JsonUtil.jsonDeserialization(payload, HashMap.class) : new HashMap();
            RInstanceTree instanceTree = InstanceManager.getInstanceTree(processInstanceId);
            if (instanceTree == null) {
                log.warn(String.format("[%s]Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s ), but tree not exist, ignored.",
                        processInstanceId, bo, on, event, payload));
                return;
            }
            RTreeNode mainBONode = instanceTree.Root;
            if (mainBONode == null) {
                log.warn(String.format("[%s]Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s ), but main BO not exist, ignored.",
                        processInstanceId, bo, on, event, payload));
                return;
            }
            log.info(String.format("[%s]Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s ).", processInstanceId, bo, on, event, payload));
            MultiStateMachineDispatcher dispatcher = (MultiStateMachineDispatcher) mainBONode.getExect().getEventDispatcher();
            BOXMLExecutionContext ctx = mainBONode.getExect();
            dispatcher.send(ctx.processInstanceId, ctx.NodeId, "", MessageMode.UNICAST, bo, "",
                    BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR, event, payloadMap, "", 0);
        } catch (Exception e) {
            log.error(String.format("[%s]Dispatch callback(BO:%s | ON:%s | EVT:%s | P:%s ), but exception occurred, %s",
                    processInstanceId, bo, on, event, payload, e));
            throw new ServiceFailureException(e);
        }
    }

    @Override
    public void dispatchCallbackByNotifiableId(String processInstanceId, String notifiableId, String on, String event, String payload) {
        try {
            HashMap payloadMap = payload != null ? JsonUtil.jsonDeserialization(payload, HashMap.class) : new HashMap();
            RInstanceTree instanceTree = InstanceManager.getInstanceTree(processInstanceId);
            if (instanceTree == null) {
                log.warn(String.format("[%s]Dispatch callback(Notifiable:%s | ON:%s | EVT:%s | P:%s ), but tree not exist, ignored.",
                        processInstanceId, notifiableId, on, event, payload));
                return;
            }
            RTreeNode mainBONode = instanceTree.Root;
            if (mainBONode == null) {
                log.warn(String.format("[%s]Dispatch callback(Notifiable:%s | ON:%s | EVT:%s | P:%s ), but main BO not exist, ignored.",
                        processInstanceId, notifiableId, on, event, payload));
                return;
            }
            log.info(String.format("[%s]Dispatch callback(Notifiable:%s | ON:%s | EVT:%s | P:%s ).", processInstanceId, notifiableId, on, event, payload));
            MultiStateMachineDispatcher dispatcher = (MultiStateMachineDispatcher) mainBONode.getExect().getEventDispatcher();
            BOXMLExecutionContext ctx = mainBONode.getExect();
            dispatcher.send(ctx.processInstanceId, ctx.NodeId, "", MessageMode.TO_NOTIFIABLE_ID, notifiableId, "",
                    BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR, event, payloadMap, "", 0);
        } catch (Exception e) {
            log.warn(String.format("[%s]Dispatch callback(Notifiable:%s | ON:%s | EVT:%s | P:%s ), but exception occurred, %s",
                    processInstanceId, notifiableId, on, event, payload, e));
            throw new ServiceFailureException(e);
        }
    }
}
