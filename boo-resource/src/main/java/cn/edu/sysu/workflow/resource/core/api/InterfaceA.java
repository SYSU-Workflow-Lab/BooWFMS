package cn.edu.sysu.workflow.resource.core.api;

import cn.edu.sysu.workflow.common.constant.LocationContext;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.enums.WorkItemStatus;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.resource.core.context.TaskItemContext;
import cn.edu.sysu.workflow.resource.core.context.WorkItemContext;
import cn.edu.sysu.workflow.resource.dao.ServiceInfoDAO;
import cn.edu.sysu.workflow.resource.service.TaskItemContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage : Implementation of Interface A of Resource Service.
 * Interface A is responsible for process load and unload, launching, and other
 * service requests passing from NameService or BOEngine.
 */

@Service
public class InterfaceA {

    private static final Logger log = LoggerFactory.getLogger(InterfaceA.class);

    @Autowired
    private ServiceInfoDAO serviceInfoDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Lazy
    private InterfaceB interfaceB;

    @Autowired
    private TaskItemContextService taskItemContextService;

    /**
     * Handle resourcing submission request from BO Engine.
     *
     * @param processInstanceId process instance id
     * @param boName            task belong to BO name
     * @param nodeId            producer tree node global id
     * @param polymorphismName  task name defined in BO XML.
     * @param arguments         arguments list in JSON string
     * @return response package
     */
    public void engineSubmitTask(String processInstanceId, String boName, String nodeId, String polymorphismName, String arguments) {
        try {
            TaskItemContext taskItemContext = taskItemContextService.getContext(processInstanceId, boName, polymorphismName);
            HashMap argMap = JsonUtil.jsonDeserialization(arguments, HashMap.class);
            interfaceB.performEngineSubmitTask(taskItemContext, nodeId, argMap, processInstanceId);
        } catch (Exception ex) {
            log.error("[" + processInstanceId + "Exception in EngineSubmitTask, " + ex);
            throw new ServiceFailureException(ex);
        }
    }

    /**
     * Handle resourcing submission request from BO Engine.
     *
     * @param processInstanceId process instance id
     * @param successFlag       process finish status
     * @return response package
     */
    public void engineFinishProcess(String processInstanceId, String successFlag) {
        interfaceB.performEngineFinishProcess(processInstanceId, successFlag == null ? "1" : successFlag);
    }

    /**
     * Handle callback and hook notification when work item status changed.
     *
     * @param statusType      destination status type
     * @param workItemContext work item context
     * @param taskItemContext task item context
     * @param payloadJSON     payload in JSON encoded string
     */
    public void handleCallbackAndHook(WorkItemStatus statusType, WorkItemContext workItemContext, TaskItemContext taskItemContext, String payloadJSON) {
        String processInstanceId = workItemContext.getWorkItem().getProcessInstanceId();
        String bo = workItemContext.getWorkItem().getCallbackNodeId();
        // events
        List<String> callbacks = taskItemContextService.getCallbackEventsOfStatus(taskItemContext, statusType);
        for (String cb : callbacks) {
            HashMap<String, String> argsMap = new HashMap<>();
            argsMap.put("processInstanceId", processInstanceId);
            argsMap.put("bo", bo);
            argsMap.put("on", statusType.name());
            argsMap.put("event", cb);
            if (payloadJSON != null) {
                argsMap.put("payload", payloadJSON);
            }
            restTemplate.postForObject(serviceInfoDAO.findEngineServiceUrlByProcessInstanceId(processInstanceId) + LocationContext.URL_ENGINE_CALLBACK, argsMap, String.class);
        }
        // hooks
        List<String> hooks = taskItemContextService.getCallbackHooksOfStatus(taskItemContext, statusType);
        for (String hk : hooks) {
            HashMap<String, String> argsMap = new HashMap<>();
            argsMap.put("processInstanceId", processInstanceId);
            argsMap.put("bo", bo);
            argsMap.put("on", statusType.name());
            if (payloadJSON != null) {
                argsMap.put("payload", payloadJSON);
            }
            // NOTICE: here does not internal interaction, DO NOT use interaction router!
            // TODO: Here should check whether hook URL is to local or not, since internal post here is very dangerous!
            // TODO: All hook URL with `localhost` or `127.0.0.1` should be banned!
            try {
                restTemplate.postForObject(hk, argsMap, String.class);
            }
            // just jump those failed to send, do not throw exception to stop all
            catch (Exception ex) {
                log.error(String.format("[%s]Cannot handle hook (%s) for work item %s, %s",
                        processInstanceId, hk, workItemContext.getWorkItem().getWorkItemId(), ex));
            }
        }
    }
}
