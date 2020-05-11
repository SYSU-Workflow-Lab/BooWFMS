package cn.edu.sysu.workflow.engine.service;

import cn.edu.sysu.workflow.engine.core.model.SCXML;

import java.util.AbstractMap;
import java.util.List;

/**
 * 流程实例管理服务
 *
 * @author Ariana, Rinkako, Skye
 * Created on 2019/12/31
 */
public interface ProcessInstanceManagementService {

    /**
     * obtain main bo xml content from database according to the process id, and then read and execute it
     *
     * @param processInstanceId
     */
    void launchProcess(String processInstanceId);

    /**
     * Upload a BO for a specific process.
     *
     * @param businessProcessId  belong to business process id
     * @param businessObjectName BO name
     * @param content            BO content string
     * @return pair of businessObjectId - involved business role names string
     */
    AbstractMap.SimpleEntry<String, List<String>> uploadBusinessObject(String businessProcessId, String businessObjectName, String content);

    /**
     * Get a user-friendly descriptor of an instance tree.
     *
     * @param processInstanceId
     * @return a descriptor of span instance tree JSON descriptor
     */
    String getSpanTreeDescriptor(String processInstanceId);

    /**
     * execute the main bo of the current process
     *
     * @param scxml
     * @param processInstanceId
     * @param processId
     */
    void executeBO(SCXML scxml, String processInstanceId, String processId);

}
