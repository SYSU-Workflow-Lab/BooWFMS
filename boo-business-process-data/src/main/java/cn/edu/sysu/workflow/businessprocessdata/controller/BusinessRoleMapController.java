package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.businessprocessdata.service.BusinessRoleMapService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理业务角色映射相关请求
 *
 * @author Skye
 * Created on 2020/4/27
 */
@RestController
@RequestMapping("/rolemap")
public class BusinessRoleMapController {

    @Autowired
    private BusinessRoleMapService businessRoleMapService;

    /**
     * Register a business role map.
     *
     * @param processInstanceId process instance id
     * @param organizationId    organization global id
     * @param dataVersion       organization data version
     * @param map               map descriptor
     * @return response package
     */
    @RequestMapping(value = "/register")
    public BooReturnForm register(@RequestParam(value = "processInstanceId") String processInstanceId,
                                  @RequestParam(value = "organizationId") String organizationId,
                                  @RequestParam(value = "dataVersion") String dataVersion,
                                  @RequestParam(value = "map") String map) {

        // logic
        businessRoleMapService.register(processInstanceId, organizationId, dataVersion, map);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("register business role map successful");
        return booReturnForm;
    }

    /**
     * Load involved resources to participant for RS.
     *
     * @param accountId         account id
     * @param processInstanceId process instance id
     * @return response package
     */
    @RequestMapping(value = "/loadParticipant")
    public BooReturnForm loadParticipant(@RequestParam(value = "accountId") String accountId,
                                         @RequestParam(value = "processInstanceId") String processInstanceId) {
        // logic
        businessRoleMapService.loadParticipant(accountId, processInstanceId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("load business participants successful");
        return booReturnForm;
    }
}
