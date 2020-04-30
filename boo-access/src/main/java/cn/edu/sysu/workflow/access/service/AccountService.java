package cn.edu.sysu.workflow.access.service;

/**
 * 账户服务
 *
 * @author Skye
 * Created on 2020/4/28
 */
public interface AccountService {

    /**
     * 注册账户
     *
     * @param username
     * @param password
     * @param organization
     * @param level
     * @return
     */
    void register(String username, String password, String organization, String level);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 更新账户信息
     *
     * @param username
     * @param organization
     * @param status
     * @param level
     */
    void update(String username, String organization, Integer status, String level);

    /**
     * 删除账户
     *
     * @param username
     */
    void delete(String username);

    /**
     * 根据用户名查询是否存在账户
     *
     * @param username
     * @return
     */
    boolean checkAccountByUsername(String username);

}
