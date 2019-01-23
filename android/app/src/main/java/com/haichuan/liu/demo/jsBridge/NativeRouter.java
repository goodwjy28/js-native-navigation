package com.haichuan.liu.demo.jsBridge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lhc on 2019/1/21.
 */

public class NativeRouter {

    public static final String ROUTER_BASE_URL = "http://192.168.1.112:8080";
    static private Map<String, String> mapper = new HashMap<>();

    static {
        mapper.put("detail", "com.haichuan.liu.demo.DetailActivity");
    }

    static Class push(String key) {
        String className = mapper.get(key);
        if (className != null && !"".equals(className)) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
