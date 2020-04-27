package cn.edu.sysu.workflow.businessprocessdata.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Rinkako
 * Date  : 2018/1/21
 * Usage : Parse role map description string from service request.
 */
public final class RoleMapParser {
    /**
     * Parse role map register descriptor to string pair of map relation.
     *
     * @param parseStr request descriptor string
     * @return a list of pair of role map relation with pattern (BusinessRole, GlobalId)
     */
    public static List<AbstractMap.SimpleEntry<String, String>> parse(final String parseStr) {
        if (parseStr == null) {
            throw new IllegalArgumentException();
        }
        List<AbstractMap.SimpleEntry<String, String>> retList = new ArrayList<>();
        String[] parseItem = parseStr.split(";");
        for (String kvpStr : parseItem) {
            String[] kvpItem = kvpStr.split(",");
            AbstractMap.SimpleEntry<String, String> kvp = new AbstractMap.SimpleEntry<>(kvpItem[0], kvpItem[1]);
            retList.add(kvp);
        }
        return retList;
    }
}
