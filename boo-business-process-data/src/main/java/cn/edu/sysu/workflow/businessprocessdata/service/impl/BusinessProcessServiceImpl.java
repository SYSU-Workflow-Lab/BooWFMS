package cn.edu.sysu.workflow.businessprocessdata.service.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.BusinessProcessDAO;
import cn.edu.sysu.workflow.businessprocessdata.service.BusinessProcessService;
import cn.edu.sysu.workflow.common.entity.BusinessProcess;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.util.AuthDomainHelper;
import cn.edu.sysu.workflow.common.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link BusinessProcessService}
 *
 * @author Skye
 * Created on 2020/4/17
 */
@Service
public class BusinessProcessServiceImpl implements BusinessProcessService {

    private static final Logger log = LoggerFactory.getLogger(BusinessProcessServiceImpl.class);

    @Autowired
    private BusinessProcessDAO businessProcessDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcess(String creatorId, String processName, String mainBusinessObjectName) {
        try {
            BusinessProcess businessProcess = new BusinessProcess();
            businessProcess.setBusinessProcessId(BusinessProcess.PREFIX + IdUtil.nextId());
            businessProcess.setCreatorId(creatorId);
            businessProcess.setMainBusinessObjectName(mainBusinessObjectName);
            businessProcess.setBusinessProcessName(processName);
            businessProcess.setAverageCost(0L);
            businessProcess.setLaunchCount(0);
            businessProcess.setSuccessCount(0);
            businessProcessDAO.save(businessProcess);
            return businessProcess.getBusinessProcessId();
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("Create business process but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("Create business process but exception occurred, service rollback", ex);
        }
    }

    @Override
    public BusinessProcess findOne(String businessProcessId) {
        try {
            return businessProcessDAO.findOne(businessProcessId);
        } catch (Exception ex) {
            log.error("Get Processes of pid but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("Get Processes of pid but exception occurred", ex);
        }
    }

    @Override
    public List<BusinessProcess> findBusinessProcessesByCreatorId(String creatorId) {
        try {
            return businessProcessDAO.findBusinessProcessesByCreatorId(creatorId);
        } catch (Exception ex) {
            log.error("Get business processes of creator but exception occurred, " + ex);
            throw new ServiceFailureException("Get business processes of creator but exception occurred", ex);
        }
    }

    @Override
    public List<BusinessProcess> findBusinessProcessesByOrganization(String organization) {
        try {
            List<BusinessProcess> qRet = businessProcessDAO.findBusinessProcessesByOrganization("@" + organization);
            List<BusinessProcess> pureRet = new ArrayList<>();
            for (BusinessProcess bp : qRet) {
                if (AuthDomainHelper.getDomainByAuthName(bp.getCreatorId()).equals(organization)) {
                    pureRet.add(bp);
                }
            }
            return pureRet;
        } catch (Exception ex) {
            log.error("Get Processes of domain but exception occurred, " + ex);
            throw new ServiceFailureException("Get Processes of domain but exception occurred", ex);
        }
    }

    @Override
    public boolean containsBusinessProcess(String creatorId, String processName) {
        try {
            return businessProcessDAO.checkBusinessProcessByCreatorIdAndProcessName(creatorId, processName);
        } catch (Exception ex) {
            log.error("Get BO in Process but exception occurred, " + ex);
            throw new ServiceFailureException("\"Get BO in Process but exception occurred", ex);
        }
    }

}
