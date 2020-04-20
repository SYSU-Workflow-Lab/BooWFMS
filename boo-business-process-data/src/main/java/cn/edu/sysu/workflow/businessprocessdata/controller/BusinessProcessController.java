package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.businessprocessdata.service.BusinessProcessService;
import cn.edu.sysu.workflow.common.entity.BusinessProcess;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处理业务流程相关请求
 *
 * @author Skye
 * Created on 2020/4/17
 */
@RestController
@RequestMapping("/process")
public class BusinessProcessController {

    @Autowired
    private BusinessProcessService businessProcessService;

    /**
     * Create a new process for a user.
     *
     * @param creatorId creator id
     * @param name      process unique name
     * @param mainbo    main bo name
     * @return response package
     */
    @RequestMapping(value = "/createProcess")
    public BooReturnForm createProcess(@RequestParam(value = "creatorId") String creatorId,
                                       @RequestParam(value = "name") String name,
                                       @RequestParam(value = "mainbo") String mainbo) {
        // logic
        String data = businessProcessService.createProcess(creatorId, name, mainbo);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("create process successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get process list of a specific creator.
     *
     * @param creatorId creator id
     * @return response package
     */
    @RequestMapping(value = "/findBusinessProcessesByCreatorId")
    public BooReturnForm findBusinessProcessesByCreatorId(@RequestParam(value = "creatorId") String creatorId) {
        // logic
        List<BusinessProcess> data = businessProcessService.findBusinessProcessesByCreatorId(creatorId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("find business processes by creatorId successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get process list of a specific organization.
     *
     * @param organization organization name
     * @return response package
     */
    @RequestMapping(value = "/findBusinessProcessesByOrganization")
    public BooReturnForm findBusinessProcessesByOrganization(@RequestParam(value = "organization") String organization) {
        // logic
        List<BusinessProcess> data = businessProcessService.findBusinessProcessesByOrganization(organization);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("find business processes by organization successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get process list of a specific domain.
     *
     * @param pid   process global id
     * @return response package
     */
    @RequestMapping(value = "/findBusinessProcessByBusinessProcessId")
    public BooReturnForm findBusinessProcessByBusinessProcessId(@RequestParam(value = "pid") String pid) {
        // logic
        BusinessProcess data = businessProcessService.findBusinessProcessByBusinessProcessId(pid);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("find business process by pid successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }
}
