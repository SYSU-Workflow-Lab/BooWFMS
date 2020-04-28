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
     *                <li>organizationName</li>
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

    /**
     * 根据用户名查询盐值
     *
     * @param username
     * @return
     */
    String findSaltByUsername(String username);

    /**
     * 根据账户名和密码查询是否存在（用于登录）
     *
     * @param username
     * @param password
     * @return
     */
    Boolean checkAccountByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查询账户是否存在
     *
     * @param username
     * @return
     */
    Boolean checkAccountByUsername(String username);

}
