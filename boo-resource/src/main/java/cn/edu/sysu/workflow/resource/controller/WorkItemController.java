package cn.edu.sysu.workflow.resource.controller;

import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.resource.core.api.InterfaceW;
import cn.edu.sysu.workflow.resource.service.WorkItemContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: Rinkako, Skye
 * Date  : 2018/2/22
 * Usage : Handle requests about work item.
 */
@RestController
@RequestMapping("/workitem")
public class WorkItemController {

    /**
     * WorkItemContext Handler.
     */
    @Autowired
    private WorkItemContextService workItemContextService;

    @Autowired
    private InterfaceW interfaceW;

    /**
     * Start a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @param tokenId    auth user token id
     * @return response package in JSON
     */
    @RequestMapping(value = "/start")
    public BooReturnForm startWorkItem(@RequestParam(value = "workerId") String workerId,
                                       @RequestParam(value = "workItemId") String workItemId,
                                       @RequestParam(value = "payload", required = false) String payload,
                                       @RequestParam(value = "tokenId", required = false) String tokenId) {
        // logic
        if (interfaceW.start(workItemId, workerId, payload, tokenId)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("start work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("start work item failed");
            return booReturnForm;
        }
    }

    /**
     * Accept a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @param tokenId    auth user token id
     * @return response package in JSON
     */
    @RequestMapping(value = "/accept")
    public BooReturnForm acceptWorkItem(@RequestParam(value = "workerId") String workerId,
                                        @RequestParam(value = "workItemId") String workItemId,
                                        @RequestParam(value = "payload") String payload,
                                        @RequestParam(value = "tokenId") String tokenId) {
        // logic
        if (interfaceW.acceptOffer(workItemId, workerId, payload, tokenId)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("accept work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("accept work item failed");
            return booReturnForm;
        }
    }

    /**
     * Accept and start a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @param tokenId    auth user token id
     * @return response package in JSON
     */
    @RequestMapping(value = "/acceptStart")
    public BooReturnForm acceptAndStartWorkItem(@RequestParam(value = "workerId") String workerId,
                                                @RequestParam(value = "workItemId") String workItemId,
                                                @RequestParam(value = "payload") String payload,
                                                @RequestParam(value = "tokenId") String tokenId) {
        // logic
        if (interfaceW.acceptAndStart(workItemId, workerId, payload, tokenId)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("accept and start work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("accept and start work item failed");
            return booReturnForm;
        }
    }

    /**
     * Complete a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @return response package in JSON
     */
    @RequestMapping(value = "/complete")
    public BooReturnForm completeWorkItem(@RequestParam(value = "workerId") String workerId,
                                          @RequestParam(value = "workItemId") String workItemId,
                                          @RequestParam(value = "payload") String payload) {
        // logic
        if (interfaceW.complete(workItemId, workerId, payload)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("complete work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("complete work item failed");
            return booReturnForm;
        }
    }

    /**
     * Suspend a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @return response package in JSON
     */
    @RequestMapping(value = "/suspend")
    public BooReturnForm suspendWorkItem(@RequestParam(value = "workerId") String workerId,
                                         @RequestParam(value = "workItemId") String workItemId,
                                         @RequestParam(value = "payload") String payload) {
        // logic
        if (interfaceW.suspend(workItemId, workerId, payload)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("suspend work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("suspend work item failed");
            return booReturnForm;
        }
    }

    /**
     * Unsuspend a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @return response package in JSON
     */
    @RequestMapping(value = "/unsuspend")
    public BooReturnForm unsuspendWorkItem(@RequestParam(value = "workerId") String workerId,
                                           @RequestParam(value = "workItemId") String workItemId,
                                           @RequestParam(value = "payload") String payload) {
        // logic
        if (interfaceW.unsuspend(workItemId, workerId, payload)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("unsuspend work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("unsuspend work item failed");
            return booReturnForm;
        }
    }

    /**
     * Skip a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @return response package in JSON
     */
    @RequestMapping(value = "/skip")
    public BooReturnForm skipWorkItem(@RequestParam(value = "workerId") String workerId,
                                      @RequestParam(value = "workItemId") String workItemId,
                                      @RequestParam(value = "payload") String payload) {
        // logic
        if (interfaceW.skip(workItemId, workerId, payload)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("skip work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("skip work item failed");
            return booReturnForm;
        }
    }

    /**
     * Reallocate a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @return response package in JSON
     */
    @RequestMapping(value = "/reallocate")
    public BooReturnForm reallocateWorkItem(@RequestParam(value = "workerId") String workerId,
                                            @RequestParam(value = "workItemId") String workItemId,
                                            @RequestParam(value = "payload") String payload) {
        // logic
        if (interfaceW.reallocate(workItemId, workerId, payload)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("reallocate work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("reallocate work item failed");
            return booReturnForm;
        }
    }

    /**
     * Deallocate a work item by auth user.
     *
     * @param workerId   worker global id
     * @param workItemId work item global id
     * @param payload    payload in JSON encoded string
     * @return response package in JSON
     */
    @RequestMapping(value = "/deallocate")
    public BooReturnForm deallocateWorkItem(@RequestParam(value = "workerId") String workerId,
                                            @RequestParam(value = "workItemId") String workItemId,
                                            @RequestParam(value = "payload") String payload) {
        // logic
        if (interfaceW.deallocate(workItemId, workerId, payload)) {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("deallocate work item successful");
            return booReturnForm;
        } else {
            // return
            BooReturnForm booReturnForm = new BooReturnForm();
            booReturnForm.setMessage("deallocate work item failed");
            return booReturnForm;
        }
    }

    /**
     * Get all work items by processInstanceId.
     *
     * @param processInstanceId process instance id
     * @return response package in JSON
     */
    @RequestMapping(value = "/getAll")
    public BooReturnForm getAll(@RequestParam(value = "processInstanceId") String processInstanceId) {
        // logic
        List<MultiValueMap<String, String>> data = interfaceW.getAllActiveWorkItemsInUserFriendly(processInstanceId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("get all work items by processInstanceId successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get all work items by organization.
     *
     * @param organization organization name
     * @return response package in JSON
     */
    @RequestMapping(value = "/getAllByDomain")
    public BooReturnForm getAllByOrganization(@RequestParam(value = "organization") String organization) {
        // logic
        List<MultiValueMap<String, String>> data = interfaceW.getAllWorkItemsInUserFriendlyByOrganization(organization);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("get all work items by organization successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get all work items by participant id.
     *
     * @param workerId participant worker global id
     * @return response package in JSON
     */
    @RequestMapping(value = "/getAllByParticipant")
    public BooReturnForm getAllForParticipant(@RequestParam(value = "workerId") String workerId) {
        // logic
        List<MultiValueMap<String, String>> data = interfaceW.getAllWorkItemsInUserFriendlyByParticipant(workerId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("get all work items by participant successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get a work item.
     *
     * @param workItemId work item id
     * @return response package in JSON
     */
    @RequestMapping(value = "/get")
    public BooReturnForm getByWorkItemId(@RequestParam(value = "workItemId") String workItemId) {
        // logic
        MultiValueMap<String, String> data = interfaceW.getWorkItemInFriendly(workItemId);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("get work item by workItemId successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }
}
