package cn.edu.sysu.workflow.common.util;

/**
 * Author: Rinkako
 * Date  : 2018/2/21
 * Usage : Some useful helper methods for auth domain.
 */
public class AuthDomainHelper {

    /**
     * Get domain name by processInstanceId.
     *
     * @param processInstanceId process instance id
     * @return domain name of this process instance
     */
    public static String getDomainByProcessInstanceId(String processInstanceId) {
        return AuthDomainHelper.getDomainByAuthName(AuthDomainHelper.getAuthNameByProcessInstanceId(processInstanceId));
    }

    /**
     * Get auth name by processInstanceId.
     *
     * @param processInstanceId process instance id
     * @return domain name of this process instance
     */
    public static String getAuthNameByProcessInstanceId(String processInstanceId) {
        return processInstanceId.split("_")[1];
    }

    /**
     * Get domain name by auth name.
     *
     * @param authName full auth name
     * @return domain name of this process instance
     */
    public static String getDomainByAuthName(String authName) {
        return authName.split("@")[1];
    }

    /**
     * Check if a auth user belong to a specific domain.
     *
     * @param authName auth user name
     * @param domain   domain name
     * @return true if auth user belong to domain
     */
    public static Boolean isAuthUserInDomain(String authName, String domain) {
        return AuthDomainHelper.getDomainByAuthName(authName).equals(domain);
    }
}
