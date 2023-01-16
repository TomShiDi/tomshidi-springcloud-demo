package com.tomshidi.demo.provider.service.impl;

import com.tomshidi.demo.interfaces.service.IDemoRmiService;

/**
 * @author tomshidi
 * @date 2022/6/14 17:53
 */
public class DemoRmiServiceImpl implements IDemoRmiService {

    @Override
    public String strConcat(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return null;
        }
        return s1 + s2;
    }
}
