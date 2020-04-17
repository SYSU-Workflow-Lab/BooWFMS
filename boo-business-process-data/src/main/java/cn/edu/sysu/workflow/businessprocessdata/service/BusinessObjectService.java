package cn.edu.sysu.workflow.businessprocessdata.service;

import java.util.AbstractMap;

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

}
