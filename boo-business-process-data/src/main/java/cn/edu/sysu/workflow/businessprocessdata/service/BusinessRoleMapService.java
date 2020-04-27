package cn.edu.sysu.workflow.businessprocessdata.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 业务角色映射服务
 *
 * @author Skye
 * Created on 2020/4/27
 */
public interface BusinessRoleMapService {

    /**
     * Register role map service for a specific process runtime.
     *
     * @param processInstanceId process instance id
     * @param organizationId    organization global id
     * @param dataVersion       organization data version
     * @param mapDescriptor        register parameter descriptor string
     */
    void register(String processInstanceId, String organizationId, String dataVersion, String mapDescriptor);

    /**
     * Load participants for a process to be launched soon.
     *
     * @param accountId user account id
     * @param processInstanceId  process instance id
     */
    void loadParticipant(String accountId, String processInstanceId);

}
