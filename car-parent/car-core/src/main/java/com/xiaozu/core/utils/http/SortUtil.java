package com.xiaozu.core.utils.http;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SortUtil {

    /**
     * 参数按照首字母排序
     *
     * @param map
     * @return
     */
    public static TreeMap sortMap(Map map) {
        TreeMap treeMap = new TreeMap<>(map);
        return treeMap;
    }

    /**
     * 参数首字符排序后组装成字符串
     *
     * @param map
     * @return
     */
    public static String assembleSortMap(Map map) {
        TreeMap treeMap = sortMap(map);
        Set<Map.Entry> entrySet = treeMap.entrySet();
        List<String> list = new LinkedList<>();
        for (Map.Entry entry : entrySet) {
            list.add(StringUtils.join(Arrays.asList("&", entry.getKey(), "=", entry.getValue()), ""));
        }
        String params = StringUtils.join(list, "");
        params = params.substring(1);
        return params;
    }
}
