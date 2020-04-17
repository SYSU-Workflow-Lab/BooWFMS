package cn.edu.sysu.workflow.businessprocessdata.controller;

import cn.edu.sysu.workflow.businessprocessdata.service.BusinessProcessService;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.common.entity.exception.MissingParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
     * @param token  auth token
     * @param creatorId  creator id
     * @param name   process unique name
     * @param mainbo main bo name
     * @return response package
     */
    @RequestMapping(value = "/createProcess", produces = {"application/json"})
    public BooReturnForm createProcess(@RequestParam(value = "token", required = false) String token,
                                       @RequestParam(value = "creatorId", required = false) String creatorId,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "mainbo", required = false) String mainbo) {
        // miss params
        List<String> missingParams = new ArrayList<>();
        if (StringUtils.isEmpty(token)) {
            missingParams.add("token");
        }
        if (StringUtils.isEmpty(creatorId)) {
            missingParams.add("creatorId");
        }
        if (StringUtils.isEmpty(name)) {
            missingParams.add("name");
        }
        if (StringUtils.isEmpty(mainbo)) {
            missingParams.add("mainbo");
        }
        if (missingParams.size() > 0) {
            throw new MissingParametersException(missingParams);
        }

        // logic
        String data = businessProcessService.createProcess(creatorId, name, mainbo);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("create process successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }
}
