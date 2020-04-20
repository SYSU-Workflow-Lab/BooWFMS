package cn.edu.sysu.workflow.businessprocessdata.service.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.BusinessObjectDAO;
import cn.edu.sysu.workflow.businessprocessdata.dao.ServiceInfoDAO;
import cn.edu.sysu.workflow.businessprocessdata.service.BusinessObjectService;
import cn.edu.sysu.workflow.common.constant.LocationContext;
import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import cn.edu.sysu.workflow.common.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

/**
 * {@link BusinessObjectService}
 *
 * @author Skye
 * Created on 2020/4/17
 */
@Service
public class BusinessObjectServiceImpl implements BusinessObjectService {

    private static final Logger log = LoggerFactory.getLogger(BusinessObjectServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BusinessObjectDAO businessObjectDAO;

    @Autowired
    private ServiceInfoDAO serviceInfoDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AbstractMap.SimpleEntry<String, String> uploadBusinessObject(String businessProcessId, String businessObjectName, String content) {
        boolean cmtFlag = false;
        try {
            String businessObjectId = BusinessObject.PREFIX + IdUtil.nextId();
            BusinessObject businessObject = new BusinessObject();
            businessObject.setBusinessObjectId(businessObjectId);
            businessObject.setProcessId(businessProcessId);
            businessObject.setBusinessObjectName(businessObjectName);
            businessObject.setContent(content);
            businessObjectDAO.save(businessObject);
            cmtFlag = true;
            // send to engine for get business role
            HashMap<String, String> args = new HashMap<>();
            args.put("boIdList", businessObjectId);
            String involveBRs = restTemplate.postForObject(
                    serviceInfoDAO.findEngineFeign().getUrl() + LocationContext.URL_ENGINE_SERIALIZEBO, args, String.class);
            return new AbstractMap.SimpleEntry<>(businessObjectId, involveBRs);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (cmtFlag) {
                log.error("Error in creating a new bo");
            }
            log.error("Upload BO but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("Upload BO but exception occurred, service rollback", ex);
        }
    }

    @Override
    public List<BusinessObject> findProcessBOList(String businessProcessId) {
        try {
            return businessObjectDAO.findBusinessObjectsByProcessId(businessProcessId);
        } catch (Exception ex) {
            log.error("Get BO in Process but exception occurred, service rollback, " + ex);
            throw new ServiceFailureException("Get BO in Process but exception occurred, service rollback", ex);
        }
    }

    @Override
    public BusinessObject findOne(String boId) {
        try {
            return businessObjectDAO.findOne(boId);
        } catch (Exception ex) {
            log.error("Get BO context but exception occurred, " + ex);
            throw new ServiceFailureException("Get BO context but exception occurred", ex);
        }
    }

}
