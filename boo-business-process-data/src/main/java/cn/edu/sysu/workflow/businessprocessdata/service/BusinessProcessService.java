package cn.edu.sysu.workflow.businessprocessdata.service;

import cn.edu.sysu.workflow.common.entity.BusinessProcess;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务流程数据服务
 *
 * @author Skye
 * Created on 2020/4/17
 */
public interface BusinessProcessService {

    /**
     * Create a new process.
     *
     * @param creatorId              creator id
     * @param processName            process unique name for a specific creator id
     * @param mainBusinessObjectName process entry point BO name
     * @return process pid
     */
    String createProcess(String creatorId, String processName, String mainBusinessObjectName);

    /**
     * 根据业务流程Id查找业务流程
     *
     * @param businessProcessId
     * @return
     */
    BusinessProcess findBusinessProcessByBusinessProcessId(String businessProcessId);

    /**
     * 根据创建者ID查找业务流程列表
     *
     * @param creatorId
     * @return
     */
    List<BusinessProcess> findBusinessProcessesByCreatorId(String creatorId);

    /**
     * 根据组织名查找业务流程列表
     *
     * @param organization organization name
     * @return a list of process
     */
    List<BusinessProcess> findBusinessProcessesByOrganization(String organization);

    /**
     * Check if a process name is already existing in a creator process list.
     *
     * @param creatorId   creator id
     * @param processName process name
     * @return boolean for process name existence
     */
    boolean containsBusinessProcess(String creatorId, String processName);
}
