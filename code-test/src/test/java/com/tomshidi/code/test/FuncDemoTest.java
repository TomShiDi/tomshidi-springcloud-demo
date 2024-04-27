package com.tomshidi.code.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test unit
 * @author TomShiDi
 * @since 2024/4/27 17:10
 */
class FuncDemoTest {

    @Test
    void removeRepeatStr() {
        String result = FuncDemo.removeRepeatStr("aabcccbbad");
        assertEquals("d", result);
        result = FuncDemo.removeRepeatStr("aa");
        assertEquals("aa", result);
        result = FuncDemo.removeRepeatStr("aaa");
        assertEquals("", result);
        result = FuncDemo.removeRepeatStr("aab");
        assertEquals("aab", result);
        result = FuncDemo.removeRepeatStr("aaabbb");
        assertEquals("", result);
    }

    @Test
    void replaceByBeforeChar() {
        String result = FuncDemo.replaceByBeforeChar("abcccbad");
        assertEquals("d", result);
        result = FuncDemo.replaceByBeforeChar("aa");
        assertEquals("aa", result);
        result = FuncDemo.replaceByBeforeChar("bbb");
        assertEquals("a", result);
        result = FuncDemo.replaceByBeforeChar("aab");
        assertEquals("aab", result);
        result = FuncDemo.replaceByBeforeChar("bbbccc");
        assertEquals("ab", result);
    }
}