package com.tomshidi.flume.interceptor;

import com.alibaba.fastjson.JSON;

/**
 * @author TomShiDi
 * @since 2024/3/18 11:29
 */
public class JsonUtil {
    public static boolean isJsonValidate(String json) {
        try {
            JSON.parseObject(json);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
