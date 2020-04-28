package cn.edu.sysu.workflow.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Rinkako, Skye
 * Created on 2019/9/19
 */
public final class EncryptUtil {

    /**
     * Encrypt a string using SHA256
     *
     * @param input
     * @return
     */
    public static String encryptSHA256(String input) {
        return DigestUtils.sha256Hex(input);
    }

    /**
     * 随机生成8位盐值
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            return encryptSHA256("" + random.nextInt()).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(getSalt());
    }

}
