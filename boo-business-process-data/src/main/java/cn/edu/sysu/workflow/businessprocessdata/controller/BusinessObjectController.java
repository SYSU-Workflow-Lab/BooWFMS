package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.businessprocessdata.service.BusinessObjectService;
import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
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
        AbstractMap.SimpleEntry<String, String> data = businessObjectService.uploadBusinessObject(pid, name, content);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("upload business object successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

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

}
