package cn.edu.sysu.workflow.businessprocessdata.service.impl;

import cn.edu.sysu.workflow.businessprocessdata.dao.BusinessObjectDAO;
import cn.edu.sysu.workflow.businessprocessdata.service.BusinessObjectService;
import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.exception.ServiceFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private BusinessObjectDAO businessObjectDAO;

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
