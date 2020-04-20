package cn.edu.sysu.workflow.resource.controller;

import cn.edu.sysu.workflow.common.entity.WorkItem;
import cn.edu.sysu.workflow.common.entity.base.BooReturnForm;
import cn.edu.sysu.workflow.resource.core.api.InterfaceW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Author: Rinkako, Skye
 * Date  : 2017/12/14
 * Usage : Handle requests about work item list.
 */
@RestController
@RequestMapping("/list")
public class WorkItemListController {

    @Autowired
    private InterfaceW interfaceW;

    /**
     * Get a specific work item list of a worker.
     *
     * @param processInstanceId process instance id
     * @param workerId          worker global id
     * @param type              work item list type name
     * @return response package
     */
    @RequestMapping(value = "/get")
    public BooReturnForm getWorkItemList(@RequestParam(value = "processInstanceId") String processInstanceId,
                                         @RequestParam(value = "workerId") String workerId,
                                         @RequestParam(value = "type") String type) {
        // logic
        Set<WorkItem> data = interfaceW.getWorkItemList(processInstanceId, workerId, type);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("get work item list successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }

    /**
     * Get a specific work item list of a list of workers.
     *
     * @param processInstanceId process instance id
     * @param workerIdList      worker global id list, split by `,`
     * @param type              work item list type name
     * @return response package
     */
    @RequestMapping(value = "/getlist")
    public BooReturnForm getWorkItemLists(@RequestParam(value = "processInstanceId") String processInstanceId,
                                          @RequestParam(value = "workerIdList") String workerIdList,
                                          @RequestParam(value = "type") String type) {
        // logic
        Map<String, Set<WorkItem>> data = interfaceW.getWorkItemLists(workerIdList.split(","), processInstanceId, type);

        // return
        BooReturnForm booReturnForm = new BooReturnForm();
        booReturnForm.setMessage("get work item lists successful");
        booReturnForm.setData(data);
        return booReturnForm;
    }
}
