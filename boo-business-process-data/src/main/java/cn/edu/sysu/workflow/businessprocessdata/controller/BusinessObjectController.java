package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.businessprocessdata.service.BusinessObjectService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.common.entity.exception.MissingParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;
import java.util.ArrayList;
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
     * @param pid     belong to process pid (required)
     * @param name    BO name (required)
     * @param content BO content (required)
     * @return response package
     */
    @RequestMapping(value = "/uploadBO", produces = {"application/json"})
    public BooReturnForm uploadBusinessObject(@RequestParam(value = "pid", required = false) String pid,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "content", required = false) String content) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(pid)) {
            missingParams.add("pid");
        }
        if (StringUtils.isEmpty(name)) {
            missingParams.add("name");
        }
        if (StringUtils.isEmpty(content)) {
            missingParams.add("content");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        AbstractMap.SimpleEntry<String, String> data = businessObjectService.uploadBusinessObject(pid, name, content);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("upload business object successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

}
