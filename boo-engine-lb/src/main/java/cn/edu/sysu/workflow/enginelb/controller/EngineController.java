package cn.edu.sysu.workflow.enginelb.controller;

import cn.edu.sysu.workflow.common.constant.LocationContext;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.enginelb.loadbalancer.EngineLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private EngineLoadBalancer engineLoadBalancer;

    /**
     * launch a process instance by the processInstanceId
     *
     * @param processInstanceId process instance id
     * @param accountId         launcher account id
     * @return booReturnForm
     */
    @RequestMapping(value = "/launchProcess")
    public BooReturnForm launchProcess(@RequestParam(value = "processInstanceId") String processInstanceId,
                                       @RequestParam(value = "accountId") String accountId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("processInstanceId", processInstanceId);
        params.add("accountId", accountId);
        return engineLoadBalancer.randomChoose(LocationContext.URL_ENGINE_START, params);
    }


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
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("pid", pid);
        params.add("name", name);
        params.add("content", content);
        return engineLoadBalancer.roundRobinChoose(LocationContext.URL_ENGINE_UPLOAD, params);
    }

    /**
     * Get a user-friendly descriptor of an instance tree.
     *
     * @param processInstanceId process instance id
     * @return booReturnForm
     */
    @RequestMapping(value = "/getSpanTree")
    public BooReturnForm getSpanTreeByProcessInstanceId(@RequestParam(value = "processInstanceId") String processInstanceId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("processInstanceId", processInstanceId);
        return engineLoadBalancer.assignationChoose(LocationContext.URL_ENGINE_SPANTREE, params);
    }

    /**
     * Resume a process instance from entity binlog.
     *
     * @param processInstanceId process process id
     * @return booReturnForm
     */
    @RequestMapping(value = "/resume")
    public BooReturnForm resume(@RequestParam(value = "processInstanceId") String processInstanceId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("processInstanceId", processInstanceId);
        return engineLoadBalancer.assignationChoose(LocationContext.URL_ENGINE_RESUME, params);
    }

    /**
     * Resume many process instances from entity binlog.
     *
     * @param processInstanceIdList process instance id in JSON list
     * @return booReturnForm
     */
    @RequestMapping(value = "/resumeMany")
    public BooReturnForm resumeMany(@RequestParam(value = "processInstanceIdList") String processInstanceIdList) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("processInstanceIdList", processInstanceIdList);
        return engineLoadBalancer.assignationChoose(LocationContext.URL_ENGINE_RESUMEMANY, params);
    }

    /**
     * Receive callback event.
     *
     * @param processInstanceId process instance id
     * @param bo                from which BO
     * @param on                which callback scene
     * @param id
     * @param event             event send to engine
     * @param payload           event send to engine
     * @return booReturnForm
     */
    @RequestMapping(value = "/callback")
    public BooReturnForm callback(@RequestParam(value = "processInstanceId") String processInstanceId,
                                  @RequestParam(value = "bo", required = false) String bo,
                                  @RequestParam(value = "on") String on,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "event") String event,
                                  @RequestParam(value = "payload", required = false) String payload) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("processInstanceId", processInstanceId);
        if (!StringUtils.isEmpty(bo)) {
            params.add("bo", bo);
        }
        params.add("on", on);
        if (!StringUtils.isEmpty(id)) {
            params.add("id", id);
        }
        params.add("event", event);
        if (!StringUtils.isEmpty(payload)) {
            params.add("payload", payload);
        }
        return engineLoadBalancer.assignationChoose(LocationContext.URL_ENGINE_CALLBACK, params);
    }

}
