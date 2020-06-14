package cn.edu.sysu.workflow.activiti.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

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
     * Map转MultiValueMap
     */
    public static MultiValueMap<String, Object> map2MultiValueMap(Map<String, Object> variables) {
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        for(Map.Entry<String, Object> entry : variables.entrySet()) {
            valueMap.set(entry.getKey(), entry.getValue());
        }
        return valueMap;
    }

}
