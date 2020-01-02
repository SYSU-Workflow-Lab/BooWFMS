package cn.edu.sysu.workflow.engine.service;

import cn.edu.sysu.workflow.engine.core.model.ModelException;
import cn.edu.sysu.workflow.engine.core.model.SCXML;

import java.util.Set;

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
     * Serialize a list of BO by their id and return involved business role names.
     *
     * @param boIdList
     * @return
     */
    Set<String> serializeBO(String boIdList);

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
