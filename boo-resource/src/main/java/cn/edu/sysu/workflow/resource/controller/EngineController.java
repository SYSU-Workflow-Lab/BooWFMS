package cn.edu.sysu.workflow.resource.controller;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.common.entity.exception.MissingParametersException;
import cn.edu.sysu.workflow.resource.core.api.InterfaceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Rinkako, Skye
 * Date  : 2018/1/19
 * Usage : Handle requests from BO Engine and Access Engine.
 */

@RestController
@RequestMapping("/internal")
public class EngineController {

    /**
     * @see InterfaceA
     */
    @Autowired
    private InterfaceA interfaceA;

    /**
     * Submit a task resourcing request from BOEngine.
     *
     * @param processInstanceId  process instance id (required)
     * @param businessObjectName bo name (required)
     * @param nodeId             id of instance tree node which produce this task (required)
     * @param polymorphismName   task polymorphism name (required)
     * @param args               argument
     * @return response package
     */
    @PostMapping(value = "/submitTask", produces = {"application/json", "application/xml"})
    public BooReturnForm submitTask(@RequestParam(value = "processInstanceId", required = false) String processInstanceId,
                                    @RequestParam(value = "businessObjectName", required = false) String businessObjectName,
                                    @RequestParam(value = "nodeId", required = false) String nodeId,
                                    @RequestParam(value = "polymorphismName", required = false) String polymorphismName,
                                    @RequestParam(value = "args", required = false) String args) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (StringUtils.isEmpty(businessObjectName)) {
            missingParams.add("businessObjectName");
        }
        if (StringUtils.isEmpty(nodeId)) {
            missingParams.add("nodeId");
        }
        if (StringUtils.isEmpty(polymorphismName)) {
            missingParams.add("polymorphismName");
        }
        if (StringUtils.isEmpty(args)) {
            missingParams.add("args");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        interfaceA.engineSubmitTask(processInstanceId, businessObjectName, nodeId, polymorphismName, args);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("task submit successful");
        return booReturnForm;
    }

    /**
     * Signal that a process instance has already finished.
     *
     * @param processInstanceId process instance id (required)
     * @param successFlag       success flag, 0 unknown, 1 success, -1 failed, default by 1
     * @return response package
     */
    @PostMapping(value = "/finProcessInstance", produces = {"application/json"})
    @ResponseBody
    public BooReturnForm finProcessInstance(@RequestParam(value = "processInstanceId", required = false) String processInstanceId,
                                            @RequestParam(value = "successFlag", required = false) String successFlag) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(processInstanceId)) {
            missingParams.add("processInstanceId");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        interfaceA.engineFinishProcess(processInstanceId, successFlag);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("process instance finish successful");
        return booReturnForm;
    }
}
