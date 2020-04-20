package cn.edu.sysu.workflow.businessprocessdata.service;

import cn.edu.sysu.workflow.common.entity.BusinessObject;

import java.util.AbstractMap;
import java.util.List;

/**
 * 业务对象数据服务
 *
 * @author Skye
 * Created on 2020/4/17
 */
public interface BusinessObjectService {

    /**
     * Upload a BO for a specific process.
     *
     * @param businessProcessId  belong to business process id
     * @param businessObjectName BO name
     * @param content            BO content string
     * @return pair of businessObjectId - involved business role names string
     */
    AbstractMap.SimpleEntry<String, String> uploadBusinessObject(String businessProcessId, String businessObjectName, String content);

    /**
     * Get the BOs in a process.
     *
     * @param businessProcessId business process id
     * @return a list of BO in the specific process
     */
    List<BusinessObject> findProcessBOList(String businessProcessId);

    /**
     * find a BO context by its id.
     *
     * @param boId BO unique id
     * @return BusinessObject instance
     */
    BusinessObject findOne(String boId);
}
