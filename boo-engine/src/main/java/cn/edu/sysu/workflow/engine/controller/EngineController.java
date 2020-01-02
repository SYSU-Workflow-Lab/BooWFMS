package cn.edu.sysu.workflow.engine.controller;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.common.entity.exception.MissingParametersException;
import cn.edu.sysu.workflow.engine.service.InteractionService;
import cn.edu.sysu.workflow.engine.service.ProcessInstanceManagementService;
import cn.edu.sysu.workflow.engine.service.SteadyStepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Handle requests passed to engine, like process launching or delegation.
 *
 * @author Rinkako, Skye
 * Created on 2020/1/2
 */
@RestController
@RequestMapping("/engine")
public class EngineController {

    private static final Logger log = LoggerFactory.getLogger(EngineController.class);

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private ProcessInstanceManagementService processInstanceManagementService;

    @Autowired
    private SteadyStepService steadyStepService;

    /**
     * launch a process by the processInstanceId
     *
     * @param processInstanceId process instance id
     * @return booReturnForm
     */
    @RequestMapping(value = "/launchProcess", produces = {"application/json"})
    public BooReturnForm launchProcess(@RequestParam(value = "processInstanceId", required = false) String processInstanceId) throws Exception {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        processInstanceManagementService.launchProcess(processInstanceId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("process launch successful");
        return booReturnForm;
    }

    /**
     * Serialized pre-stored BO XML text and return the involved BO list.
     *
     * @param boIdList BOs to be serialized, separated by `,`
     * @return booReturnForm
     */
    @RequestMapping(value = "/serializeBO", produces = {"application/json"})
    public BooReturnForm serializeBO(@RequestParam(value = "boIdList", required = false) String boIdList) throws Exception {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(boIdList)) {
            missingParams.add("boIdList");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        Set<String> data = processInstanceManagementService.serializeBO(boIdList);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("serialize BO successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get a user-friendly descriptor of an instance tree.
     *
     * @param processInstanceId process instance id
     * @return booReturnForm
     */
    @RequestMapping(value = "/getSpanTree", produces = {"application/json"})
    public BooReturnForm getSpanTreeByProcessInstanceId(@RequestParam(value = "processInstanceId", required = false) String processInstanceId) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        String data = processInstanceManagementService.getSpanTreeDescriptor(processInstanceId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("get span tree successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Resume a process instance from entity binlog.
     *
     * @param processInstanceId process process id
     * @return booReturnForm
     */
    @RequestMapping(value = "/resume", produces = {"application/json"})
    public BooReturnForm resume(@RequestParam(value = "processInstanceId", required = false) String processInstanceId) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        boolean data = steadyStepService.resumeSteady(processInstanceId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("resume successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Resume many process instances from entity binlog.
     *
     * @param processInstanceIdList process instance id in JSON list
     * @return booReturnForm
     */
    @RequestMapping(value = "/resumeMany", produces = {"application/json"})
    public BooReturnForm resumeMany(@RequestParam(value = "processInstanceIdList", required = false) String processInstanceIdList) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(processInstanceIdList)) {
            missingParams.add("processInstanceId");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        HashMap<String, List<String>> data = new HashMap<>();
        data.put("failed", steadyStepService.resumeSteadyMany(processInstanceIdList));
        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("resume many successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Receive callback event.
     *
     * @param processInstanceId process instance id (required)
     * @param bo                from which BO (required)
     * @param on                which callback scene (required)
     * @param event             event send to engine (required)
     * @param payload           event send to engine
     * @return response package
     */
    @RequestMapping(value = "/callback", produces = {"application/json"})
    public BooReturnForm callback(@RequestParam(value = "processInstanceId", required = false) String processInstanceId,
                                  @RequestParam(value = "bo", required = false) String bo,
                                  @RequestParam(value = "on", required = false) String on,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "event", required = false) String event,
                                  @RequestParam(value = "payload", required = false) String payload) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (StringUtils.isEmpty(on)) {
            missingParams.add("on");
        }
        if (StringUtils.isEmpty(event)) {
            missingParams.add("event");
        }
        if (StringUtils.isEmpty(bo)) {
            missingParams.add("bo");
        }
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        if (bo != null) {
            interactionService.dispatchCallbackByProcessInstanceId(processInstanceId, bo, on, event, payload);
            if (id != null) {
                log.warn("[" + processInstanceId + "]Received callback with both BO and ID, ID will be ignored.");
            }
        } else {
            interactionService.dispatchCallbackByNotifiableId(processInstanceId, id, on, event, payload);
        }

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("callback successful");
        return booReturnForm;
    }

}
