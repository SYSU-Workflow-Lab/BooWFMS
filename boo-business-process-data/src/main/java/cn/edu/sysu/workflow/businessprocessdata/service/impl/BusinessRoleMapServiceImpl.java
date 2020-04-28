package cn.edu.sysu.workflow.businessprocessdata.service.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.*;
import cn.edu.sysu.workflow.businessprocessdata.service.BusinessRoleMapService;
import cn.edu.sysu.workflow.businessprocessdata.util.RoleMapParser;
import cn.edu.sysu.workflow.common.entity.BusinessRoleMap;
import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.entity.ProcessParticipant;
import cn.edu.sysu.workflow.common.entity.access.Account;
import cn.edu.sysu.workflow.common.entity.access.Agent;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.enums.ProcessParticipantType;
import cn.edu.sysu.workflow.common.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * {@link BusinessRoleMapService}
 *
 * @author Skye
 * Created on 2020/4/27
 */
@Service
public class BusinessRoleMapServiceImpl implements BusinessRoleMapService {

    private static final Logger log = LoggerFactory.getLogger(BusinessRoleMapServiceImpl.class);

    @Autowired
    private BusinessRoleMapDAO businessRoleMapDAO;

    @Autowired
    private ProcessParticipantDAO processParticipantDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private AgentDAO agentDAO;

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String processInstanceId, String organizationId, String dataVersion, String mapDescriptor) {
        List<AbstractMap.SimpleEntry<String, String>> parsedList = RoleMapParser.parse(mapDescriptor);
        try {
            for (AbstractMap.SimpleEntry<String, String> kvp : parsedList) {
                BusinessRoleMap businessRoleMap = new BusinessRoleMap();
                businessRoleMap.setBusinessRoleMapId(BusinessRoleMap.PREFIX + IdUtil.nextId());
                businessRoleMap.setProcessInstanceId(processInstanceId);
                businessRoleMap.setBusinessRoleName(kvp.getKey());
                businessRoleMap.setOrganizationId(organizationId);
                businessRoleMap.setMappedAccountId(kvp.getValue());
                businessRoleMap.setDataVersion(dataVersion);
                businessRoleMapDAO.save(businessRoleMap);
            }
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + processInstanceId + "]When register business role map, exception occurred, " + ex.toString() + ", service rollback");
            throw new ServiceFailureException("[" + processInstanceId + "]When register business role map, exception occurred, service rollback", ex);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loadParticipant(String accountId, String processInstanceId) {
        try {
            // get involved mappings
            List<BusinessRoleMap> maps = businessRoleMapDAO.findBusinessRoleMapsByProcessInstanceId(processInstanceId);
            // decompose groups and capabilities into workers
            StringBuilder sb = new StringBuilder();
            for (BusinessRoleMap bsm : maps) {
                sb.append(bsm.getMappedAccountId()).append(":").append(bsm.getBusinessRoleName()).append(",");
            }
            String workerList = sb.toString();
            if (workerList.length() > 0) {
                workerList = workerList.substring(0, workerList.length() - 1);
            }
            // register these workers to participant
            for (BusinessRoleMap businessRoleMap : maps) {
                String mappedAccountId = businessRoleMap.getMappedAccountId();
                ProcessParticipant processParticipant = processParticipantDAO.findByAccountId(mappedAccountId);
                if (processParticipant == null) {
                    processParticipant = new ProcessParticipant();
                    processParticipant.setAccountId(mappedAccountId);
                    if (mappedAccountId.startsWith("Human_")) {
                        Account account = accountDAO.findSimpleOne(mappedAccountId);
                        processParticipant.setDisplayName(account.getUsername());
                        processParticipant.setType(ProcessParticipantType.Human.ordinal());
                    } else {
                        Agent agent = agentDAO.findOne(mappedAccountId);
                        processParticipant.setDisplayName(agent.getDisplayName());
                        processParticipant.setType(ProcessParticipantType.Agent.ordinal());
                        processParticipant.setAgentLocation(agent.getLocation());
                        processParticipant.setReentrantType(agent.getReentrantType());
                    }
                    processParticipantDAO.save(processParticipant);
                }
            }
            ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
            processInstance.setParticipantCache(workerList);
            processInstanceDAO.update(processInstance);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + processInstanceId + "]When load participant, exception occurred, " + ex.toString() + ", service rollback");
            throw new ServiceFailureException("[" + processInstanceId + "]When load participant, exception occurred, service rollback", ex);
        }
    }
}
