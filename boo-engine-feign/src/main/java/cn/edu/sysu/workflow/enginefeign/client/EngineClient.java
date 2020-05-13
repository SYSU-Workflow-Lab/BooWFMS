package cn.edu.sysu.workflow.enginefeign.client;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * boo-engine调用客户端
 *
 * @author Skye
 * Created on 2020/5/11
 */
@FeignClient(value = "engine", path = "/engine")
public interface EngineClient {

    /**
     * launch a process instance by the processInstanceId
     *
     * @param processInstanceId process instance id
     * @param accountId         launcher account id
     * @return booReturnForm
     */
    @RequestMapping(value = "/launchProcess")
    BooReturnForm launchProcess(@RequestParam(value = "processInstanceId") String processInstanceId,
                                @RequestParam(value = "accountId") String accountId);

    /**
     * Upload BO content for a process.
     *
     * @param params pid, name, content
     * @return response package
     */
    @RequestMapping(value = "/uploadBO")
    BooReturnForm uploadBusinessObject(@RequestBody MultiValueMap<String, String> params);

    /**
     * Get a user-friendly descriptor of an instance tree.
     *
     * @param processInstanceId process instance id
     * @return booReturnForm
     */
    @RequestMapping(value = "/getSpanTree")
    BooReturnForm getSpanTreeByProcessInstanceId(@RequestParam(value = "processInstanceId") String processInstanceId);

    /**
     * Resume a process instance from entity binlog.
     *
     * @param processInstanceId process process id
     * @return booReturnForm
     */
    @RequestMapping(value = "/resume")
    BooReturnForm resume(@RequestParam(value = "processInstanceId") String processInstanceId);

    /**
     * Resume many process instances from entity binlog.
     *
     * @param processInstanceIdList process instance id in JSON list
     * @return booReturnForm
     */
    @RequestMapping(value = "/resumeMany")
    BooReturnForm resumeMany(@RequestParam(value = "processInstanceIdList") String processInstanceIdList);

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
    BooReturnForm callback(@RequestParam(value = "processInstanceId") String processInstanceId,
                           @RequestParam(value = "bo", required = false) String bo,
                           @RequestParam(value = "on") String on,
                           @RequestParam(value = "id", required = false) String id,
                           @RequestParam(value = "event") String event,
                           @RequestParam(value = "payload") String payload);

}
