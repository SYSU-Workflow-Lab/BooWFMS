package cn.edu.sysu.workflow.activiti.util;

import java.io.*;
import java.util.ArrayList;

/**
 * 工具类
 *  * @author: Gordan Lin
 *  * @create: 2019/9/21
 */
public class CommonUtil {

    /**
     * 获取全部的列表元素,以split符号隔开
     */
    public static String ArrayList2String(ArrayList<String> arrayList, String split) {
        StringBuilder sb = new StringBuilder();
        for(String s : arrayList) {
            sb.append(s).append(split);
        }
        return sb.toString();
    }

    /**
     * 将对象转化为byte数组
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {

        }
        return bytes;
    }

    /**
     * 将byte数组转化为对象
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {

        }
        return obj;
    }

}
