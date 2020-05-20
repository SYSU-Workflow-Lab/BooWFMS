package cn.edu.sysu.workflow.enginefeign.controller;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.enginefeign.client.EngineClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * boo-engine调用处理器
 *
 * @author Skye
 * Created on 2020/5/11
 */
@RestController
@RequestMapping("/engine")
public class EngineController {

    @Autowired
    private EngineClient engineClient;

    @RequestMapping(value = "/echo")
    public String launchProcess(HttpSession httpSession) {
        return (String) httpSession.getAttribute("accountId");
    }

    @RequestMapping(value = "/launchProcess")
    public BooReturnForm launchProcess(@RequestParam(value = "processInstanceId") String processInstanceId,
                                       @RequestParam(value = "accountId") String accountId) {
        return engineClient.launchProcess(processInstanceId, accountId);
    }

    @RequestMapping(value = "/uploadBO")
    public BooReturnForm uploadBusinessObject(@RequestParam(value = "pid") String pid,
                                              @RequestParam(value = "name") String name,
                                              @RequestParam(value = "content") String content) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("pid", pid);
        params.add("name", name);
        params.add("content", content);
        return engineClient.uploadBusinessObject(params);
    }

    @RequestMapping(value = "/getSpanTree")
    public BooReturnForm getSpanTreeByProcessInstanceId(@RequestParam(value = "processInstanceId") String processInstanceId) {
        return engineClient.getSpanTreeByProcessInstanceId(processInstanceId);
    }

    @RequestMapping(value = "/resume")
    public BooReturnForm resume(@RequestParam(value = "processInstanceId") String processInstanceId) {
        return engineClient.resume(processInstanceId);
    }

    @RequestMapping(value = "/resumeMany")
    public BooReturnForm resumeMany(@RequestParam(value = "processInstanceIdList") String processInstanceIdList) {
        return engineClient.resumeMany(processInstanceIdList);
    }

    @RequestMapping(value = "/callback")
    public BooReturnForm callback(@RequestParam(value = "processInstanceId") String processInstanceId,
                                  @RequestParam(value = "bo", required = false) String bo,
                                  @RequestParam(value = "on") String on,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "event") String event,
                                  @RequestParam(value = "payload") String payload) {
        return engineClient.callback(processInstanceId, bo, on, id, event, payload);
    }

}
