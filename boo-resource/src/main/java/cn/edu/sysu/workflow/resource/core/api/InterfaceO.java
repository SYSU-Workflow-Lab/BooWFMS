package cn.edu.sysu.workflow.resource.core.api;

import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.enums.ResourceBindingType;
import cn.edu.sysu.workflow.resource.dao.ProcessInstanceDAO;
import cn.edu.sysu.workflow.resource.dao.ProcessParticipantDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Rinkako
 * Date  : 2018/2/9
 * Usage : Implementation of Interface O of Resource Service.
 * Interface O is responsible for resources managements. We register
 * involved resources to ProcessParticipant in Name Service. Therefore we assert
 * when Resource Service need to refer a RESOURCE,
 * it has already been registered in entity memory as a PARTICIPANT. So, this
 * interface just for involved resources information retrieving and participants
 * privileges management.
 */

@Service
public class InterfaceO {

    private static final Logger log = LoggerFactory.getLogger(InterfaceO.class);

    @Autowired
    private ProcessParticipantDAO processParticipantDAO;

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    /**
     * Get valid participant context set according to business role in specific process runtime.
     *
     * @param processInstanceId process instance id
     * @param businessRole      business role name
     * @return a Hash set for current valid participant context of a business role
     */
    public Set<ProcessParticipant> getParticipantByBRole(String processInstanceId, String businessRole) {
        HashSet<ProcessParticipant> retSet = new HashSet<>();
        try {
            ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
            String participants = processInstance.getParticipantCache();
            if (StringUtils.isEmpty(participants)) {
                return retSet;
            }
            String[] participantPairItem = participants.split(",");
            for (String workerIdBRolePair : participantPairItem) {
                String[] workerItem = workerIdBRolePair.split(":");
                if (workerItem[1].equals(businessRole)) {
                    retSet.add(processParticipantDAO.findByAccountId(workerItem[0]));
                }
            }
            return retSet;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * This method is called when sensed participant in entity is changed.
     *
     * @param processInstanceId process instance id
     * @return is fail-fast when organization data changed
     */
    public boolean senseParticipantDataChanged(String processInstanceId) {
        log.info("[" + processInstanceId + "Sensed binding resources changed.");
        ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
        return processInstance.getResourceBindingType() == ResourceBindingType.FastFail.ordinal();
    }
}
