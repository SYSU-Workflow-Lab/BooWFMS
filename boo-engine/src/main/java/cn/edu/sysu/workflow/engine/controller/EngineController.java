package cn.edu.sysu.workflow.engine.controller;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.engine.service.InteractionService;
import cn.edu.sysu.workflow.engine.service.ProcessInstanceManagementService;
import cn.edu.sysu.workflow.engine.service.SteadyStepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

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
     * launch a process instance by the processInstanceId
     *
     * @param processInstanceId process instance id
     * @param accountId         launch account id
     * @return booReturnForm
     */
    @RequestMapping(value = "/launchProcess")
    public BooReturnForm launchProcess(@RequestParam(value = "processInstanceId") String processInstanceId,
                                       @RequestParam(value = "accountId") String accountId) {
        // logic
        processInstanceManagementService.launchProcess(processInstanceId, accountId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("process instance launch successful");
        return booReturnForm;
    }

    /**
     * Upload BO content for a process.
     *
     * @param pid     belong to process pid
     * @param name    BO name
     * @param content BO content
     * @return response package
     */
    @RequestMapping(value = "/uploadBO")
    public BooReturnForm uploadBusinessObject(@RequestParam(value = "pid") String pid,
                                              @RequestParam(value = "name") String name,
                                              @RequestParam(value = "content") String content) {
        // logic
        AbstractMap.SimpleEntry<String, List<String>> data = processInstanceManagementService.uploadBusinessObject(pid, name, content);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("upload business object successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get a user-friendly descriptor of an instance tree.
     *
     * @param processInstanceId process instance id
     * @return booReturnForm
     */
    @RequestMapping(value = "/getSpanTree")
    public BooReturnForm getSpanTreeByProcessInstanceId(@RequestParam(value = "processInstanceId") String processInstanceId) {
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
    @RequestMapping(value = "/resume")
    public BooReturnForm resume(@RequestParam(value = "processInstanceId") String processInstanceId) {
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
    @RequestMapping(value = "/resumeMany")
    public BooReturnForm resumeMany(@RequestParam(value = "processInstanceIdList") String processInstanceIdList) {
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
     * @param processInstanceId process instance id
     * @param bo                from which BO
     * @param on                which callback scene
     * @param id
     * @param event             event send to engine
     * @param payload           event send to engine
     * @return response package
     */
    @RequestMapping(value = "/callback")
    public BooReturnForm callback(@RequestParam(value = "processInstanceId") String processInstanceId,
                                  @RequestParam(value = "bo", required = false) String bo,
                                  @RequestParam(value = "on") String on,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "event") String event,
                                  @RequestParam(value = "payload", required = false) String payload) {
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
