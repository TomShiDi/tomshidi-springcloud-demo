package com.tomshidi.demo.interfaces.service;

/**
 * @author tomshidi
 * @date 2022/6/14 17:50
 */
public interface IDemoRmiService {
    /**
     * 字符串拼接
     * @param s1 首字符串
     * @param s2 尾字符串
     * @return 拼接后的字符串
     */
    String strConcat(String s1, String s2);
}
