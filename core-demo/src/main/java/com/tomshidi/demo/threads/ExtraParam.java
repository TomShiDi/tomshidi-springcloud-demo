package com.tomshidi.demo.threads;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tomshidi
 * @date 2022/3/22 14:49
 */
public class ExtraParam {

    private Map<String, Object> extraParamMap = new HashMap<>();

    public void setParam(String name, Object value) {
        extraParamMap.put(name, value);
    }

    public Object getParam(String name) {
        return extraParamMap.get(name);
    }

    public Object removeParam(String name) {
        return extraParamMap.remove(name);
    }
}
