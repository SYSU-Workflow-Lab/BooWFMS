package cn.edu.sysu.workflow.access.service;

/**
 * 账户服务
 *
 * @author Skye
 * Created on 2020/4/28
 */
public interface AccountService {

    /**
     * 返回是否成功注册账户
     *
     * @param username
     * @param password
     * @param level
     * @param organization
     * @return
     */
    void register(String username, String password, String level, String organization);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 根据用户名查询是否存在账户
     *
     * @param username
     * @return
     */
    boolean checkAccountByUsername(String username);

}
