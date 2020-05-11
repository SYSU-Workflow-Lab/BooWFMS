package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.businessprocessdata.service.BusinessObjectService;
import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处理业务对象相关请求
 *
 * @author Skye
 * Created on 2020/4/17
 */
@RestController
@RequestMapping("/object")
public class BusinessObjectController {

    @Autowired
    private BusinessObjectService businessObjectService;

    /**
     * Get BO name list of a specific process.
     *
     * @param pid process id
     * @return response package
     */
    @RequestMapping(value = "/findProcessBOList")
    public BooReturnForm findProcessBOList(@RequestParam(value = "pid") String pid) {
        // logic
        List<BusinessObject> data = businessObjectService.findProcessBOList(pid);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("find process business objects successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get a BO context by its id.
     *
     * @param boId bo unique id (required)
     * @return response package
     */
    @RequestMapping(value = "/findOne")
    public BooReturnForm findOne(@RequestParam(value = "boId") String boId) {
        // logic
        BusinessObject data = businessObjectService.findOne(boId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("find process business object successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

}
