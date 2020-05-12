package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.businessprocessdata.service.ProcessInstanceService;
import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 处理业务流程实例相关请求
 *
 * @author Skye
 * Created on 2020/4/20
 */
@RestController
@RequestMapping("/instance")
public class ProcessInstanceController {

    @Autowired
    private ProcessInstanceService processInstanceService;

    /**
     * Create a request for launching a specific process.
     *
     * @param pid
     * @param from
     * @param creatorId
     * @param bindingType
     * @param launchType
     * @param failureType
     * @param binding
     * @return
     */
    @RequestMapping(value = "/create")
    public BooReturnForm create(@RequestParam(value = "pid") String pid,
                                @RequestParam(value = "from") String from,
                                @RequestParam(value = "creatorId") String creatorId,
                                @RequestParam(value = "bindingType") Integer bindingType,
                                @RequestParam(value = "launchType") Integer launchType,
                                @RequestParam(value = "failureType") Integer failureType,
                                @RequestParam(value = "binding") String binding) {
        // logic
        String data = processInstanceService.createProcessInstance(pid, from, creatorId, bindingType, launchType, failureType, binding);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("create process instance successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Check a process finish or not.
     *
     * @param processInstanceId process instance id
     * @return response package
     */
    @RequestMapping(value = "/checkFinish")
    public BooReturnForm checkFinish(@RequestParam(value = "processInstanceId") String processInstanceId) {
        // logic
        Map<String, String> data = processInstanceService.checkFinish(processInstanceId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("check process instance finish successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get runtime record by its global id.
     *
     * @param processInstanceId process instance id
     * @return response package
     */
    @RequestMapping(value = "/findOne")
    public BooReturnForm findOne(@RequestParam(value = "processInstanceId") String processInstanceId) {
        // logic
        ProcessInstance data = processInstanceService.findOne(processInstanceId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("find process instance successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }
}
