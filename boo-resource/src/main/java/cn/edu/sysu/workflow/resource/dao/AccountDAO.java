package cn.edu.sysu.workflow.resource.dao;

import cn.edu.sysu.workflow.common.entity.access.Account;

/**
 * 账户数据库操作
 *
 * @author Skye
 * Created on 2020/4/27
 */
public interface AccountDAO {

    /**
     * 根据账户Id查找账户信息（不可返回密码和盐值）
     *
     * @param accountId
     * @return
     */
    Account findSimpleOne(String accountId);

}
