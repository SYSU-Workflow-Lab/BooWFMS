package cn.edu.sysu.workflow.access.dao;

import cn.edu.sysu.workflow.common.entity.access.Authority;

/**
 * 权限数据库操作
 *
 * @author Skye
 * Created on 2020/6/10
 */
public interface AuthorityDAO {

    /**
     * 根据账户ID和业务流程实体ID查询对应权限记录
     *
     * @param accountId
     * @param businessProcessEntityId
     * @return
     */
    Authority findByAccountIdAndBusinessProcessEntityId(String accountId, String businessProcessEntityId);


}
