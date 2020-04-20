package cn.edu.sysu.workflow.resource.controller;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.resource.core.api.InterfaceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param processInstanceId  process instance id
     * @param businessObjectName bo name
     * @param nodeId             id of instance tree node which produce this task
     * @param polymorphismName   task polymorphism name
     * @param args               argument
     * @return response package
     */
    @PostMapping(value = "/submitTask")
    public BooReturnForm submitTask(@RequestParam(value = "processInstanceId") String processInstanceId,
                                    @RequestParam(value = "businessObjectName") String businessObjectName,
                                    @RequestParam(value = "nodeId") String nodeId,
                                    @RequestParam(value = "polymorphismName") String polymorphismName,
                                    @RequestParam(value = "args") String args) {
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
     * @param processInstanceId process instance id
     * @param successFlag       success flag, 0 unknown, 1 success, -1 failed, default by 1
     * @return response package
     */
    @PostMapping(value = "/finProcessInstance")
    public BooReturnForm finProcessInstance(@RequestParam(value = "processInstanceId") String processInstanceId,
                                            @RequestParam(value = "successFlag") String successFlag) {
        // logic
        interfaceA.engineFinishProcess(processInstanceId, successFlag);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("process instance finish successful");
        return booReturnForm;
    }
}
