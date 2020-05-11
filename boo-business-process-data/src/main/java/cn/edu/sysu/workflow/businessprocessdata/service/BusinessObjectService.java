package cn.edu.sysu.workflow.businessprocessdata.service;

import cn.edu.sysu.workflow.common.entity.BusinessObject;

import java.util.List;

/**
 * 业务对象数据服务
 *
 * @author Skye
 * Created on 2020/4/17
 */
public interface BusinessObjectService {

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
