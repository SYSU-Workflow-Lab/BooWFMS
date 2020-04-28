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
    boolean register(String username, String password, String level, String organization);

    /**
     * 根据用户名和组织名查询是否存在账户
     *
     * @param username
     * @param organizationName
     * @return
     */
    boolean checkAccountByUsernameAndOrganizationName(String username, String organizationName);

}
