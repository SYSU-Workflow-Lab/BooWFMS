package cn.edu.sysu.workflow.businessprocessdata.service;

/**
 * 业务流程数据服务
 * @author Skye
 * Created on 2020/4/17
 */
public interface BusinessProcessService {

    /**
     * Create a new process.
     *
     * @param creatorId       creator id
     * @param processName process unique name for a specific creator id
     * @param mainBusinessObjectName  process entry point BO name
     * @return process pid
     */
    String createProcess(String creatorId, String processName, String mainBusinessObjectName);

}
