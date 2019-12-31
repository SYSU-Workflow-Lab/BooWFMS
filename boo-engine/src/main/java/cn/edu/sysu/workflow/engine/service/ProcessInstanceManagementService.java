package cn.edu.sysu.workflow.engine.service;

/**
 * 流程实例管理服务
 *
 * @author Skye
 * Created on 2019/12/31
 */
public interface ProcessInstanceManagementService {

    /**
     * Get a user-friendly descriptor of an instance tree.
     *
     * @param processInstanceId
     * @return a descriptor of span instance tree JSON descriptor
     */
    String getSpanTreeDescriptor(String processInstanceId);

}
