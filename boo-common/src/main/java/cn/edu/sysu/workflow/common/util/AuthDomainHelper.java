package cn.edu.sysu.workflow.common.util;

/**
 * Author: Rinkako
 * Date  : 2018/2/21
 * Usage : Some useful helper methods for auth domain.
 */
public class AuthDomainHelper {

    /**
     * Get domain name by auth name.
     *
     * @param authName full auth name
     * @return domain name of this process instance
     */
    public static String getDomainByAuthName(String authName) {
        return authName.split("@")[1];
    }
}
