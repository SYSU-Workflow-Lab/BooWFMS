package cn.edu.sysu.workflow.access.dao;

import cn.edu.sysu.workflow.common.entity.access.Account;

/**
 * 账户数据库操作
 *
 * @author Skye
 * Created on 2020/4/27
 */
public interface AccountDAO {

    /**
     * 创建账户
     *
     * @param account <ul>
     *                <li>accountId</li>
     *                <li>username</li>
     *                <li>password</li>
     *                <li>salt</li>
     *                <li>organizationId</li>
     *                <li>status</li>
     *                <li>lastLoginTimestamp</li>
     *                </ul>
     * @return 新增的数据量
     */
    int save(Account account);

    /**
     * 根据账户Id查找账户信息（不可返回密码和盐值）
     *
     * @param accountId
     * @return
     */
    Account findSimpleOne(String accountId);

}
