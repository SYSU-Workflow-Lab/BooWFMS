package cn.edu.sysu.workflow.businessprocessdata.service.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.BusinessProcessDAO;
import cn.edu.sysu.workflow.businessprocessdata.dao.ProcessInstanceDAO;
import cn.edu.sysu.workflow.businessprocessdata.dao.ServiceInfoDAO;
import cn.edu.sysu.workflow.businessprocessdata.service.ProcessInstanceService;
import cn.edu.sysu.workflow.common.constant.LocationContext;
import cn.edu.sysu.workflow.common.entity.BusinessProcess;
import cn.edu.sysu.workflow.common.entity.ProcessInstance;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.util.AuthDomainHelper;
import cn.edu.sysu.workflow.common.util.IdUtil;
import cn.edu.sysu.workflow.common.util.TimestampUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ProcessInstanceService}
 *
 * @author Skye
 * Created on 2020/4/20
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    private static final Logger log = LoggerFactory.getLogger(ProcessInstanceServiceImpl.class);

    @Autowired
    private ProcessInstanceDAO processInstanceDAO;

    @Autowired
    private BusinessProcessDAO businessProcessDAO;

    @Autowired
    private ServiceInfoDAO serviceInfoDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstance(String pid, String from, Integer bindingType, Integer launchType, Integer failureType, String binding) {
        try {
            ProcessInstance processInstance = new ProcessInstance();
            processInstance.setProcessInstanceId(ProcessInstance.PREFIX + IdUtil.nextId());
            processInstance.setProcessId(pid);
            processInstance.setLaunchPlatform(from);
            processInstance.setResourceBindingType(bindingType);
            processInstance.setLaunchType(launchType);
            processInstance.setFailureType(failureType);
            processInstance.setResourceBinding(binding);
            processInstance.setResultType(0);
            processInstanceDAO.save(processInstance);
            return processInstance.getProcessInstanceId();
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(String.format("Submit process but exception occurred(pid: %s), service rollback, %s", pid, ex));
            throw new ServiceFailureException(String.format("Submit process but exception occurred(pid: %s), service rollback", pid), ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void launchProcessInstance(String processInstanceId) {
        try {
            ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
            processInstance.setLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
            String launcher = AuthDomainHelper.getAuthNameByProcessInstanceId(processInstanceId);
            processInstance.setLaunchAccountId(launcher);
            processInstanceDAO.update(processInstance);
            BusinessProcess businessProcess = businessProcessDAO.findOne(processInstance.getProcessId());
            // TODO 可能出现重复写错误，需要加锁
            businessProcess.setLaunchCount(businessProcess.getLaunchCount() + 1);
            businessProcess.setLastLaunchTimestamp(TimestampUtil.getCurrentTimestamp());
            businessProcessDAO.update(businessProcess);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[" + processInstanceId + "]Start process but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("[" + processInstanceId + "]Start process but exception occurred, service rollback", ex);
        }

        try {
            // interaction with Engine
            HashMap<String, String> args = new HashMap<>();
            args.put("processInstanceId", processInstanceId);
            restTemplate.postForObject(
                    serviceInfoDAO.findEngineFeign().getUrl() + LocationContext.URL_ENGINE_START, args, String.class);
        } catch (Exception ex) {
            log.error("Cannot interaction with Engine for processInstanceId: " + processInstanceId);
            throw new ServiceFailureException("Cannot interaction with Engine for processInstanceId: " + processInstanceId, ex);
        }
    }

    @Override
    public Map<String, String> checkFinish(String processInstanceId) {
        try {
            ProcessInstance processInstance = processInstanceDAO.findOne(processInstanceId);
            if (processInstance == null) {
                return null;
            }
            Map<String, String> retMap = new HashMap<>();
            boolean isFinished = processInstance.getFinishTimestamp() != null;
            retMap.put("IsFinished", isFinished ? "true" : "false");
            retMap.put("FinishTimestamp", isFinished ? processInstance.getFinishTimestamp().toString() : "");
            retMap.put("IsSucceed", processInstance.getResultType() == 1 ? "true" : "false");
            return retMap;
        } catch (Exception ex) {
            log.error("CheckFinish but exception occurred, " + ex);
            throw new ServiceFailureException("CheckFinish but exception occurred", ex);
        }
    }

    @Override
    public ProcessInstance findOne(String processInstanceId) {
        try {
            return processInstanceDAO.findOne(processInstanceId);
        } catch (Exception ex) {
            log.error("[" + processInstanceId + "]Get process instance but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("[" + processInstanceId + "]Get process instance but exception occurred", ex);
        }
    }
}
