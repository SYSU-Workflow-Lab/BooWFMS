package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.common.entity.human.AccountCapability;

import java.util.List;

/**
 * 账户职能映射数据库操作
 *
 * @author Skye
 * Created on 2020/5/12
 */
public interface AccountCapabilityDAO {

    /**
     * 根据职能查询相关账户职能映射
     *
     * @param capabilityId
     * @return
     */
    List<AccountCapability> findAccountCapabilityByCapabilityId(String capabilityId);

}
