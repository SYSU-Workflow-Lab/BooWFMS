package cn.edu.sysu.workflow.businessprocessdata.service.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.BusinessProcessDAO;
import cn.edu.sysu.workflow.businessprocessdata.service.BusinessProcessService;
import cn.edu.sysu.workflow.common.entity.BusinessProcess;
import cn.edu.sysu.workflow.common.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
            log.error("Create process but exception occurred, service rollback, " + ex);
            return "";
        }
    }

}
