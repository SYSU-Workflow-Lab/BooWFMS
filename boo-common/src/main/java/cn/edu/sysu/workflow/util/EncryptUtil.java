package cn.edu.sysu.workflow.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Skye on 2019/9/19.
 */
public class EncryptUtil {

    /**
     * Encrypt a string using SHA256
     *
     * @param input
     * @return
     */
    public static String EncryptSHA256(String input) {
        return DigestUtils.sha256Hex(input);
    }

}
