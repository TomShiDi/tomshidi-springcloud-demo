package com.tomshidi.base.utils;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomshidi
 * @date 2023/2/7 14:45
 */
class JsonUtilTest {

    @Test
    public void testReplaceBlank() {
        String sourceJson = "{\n" +
                "    \"person\":[\n" +
                "        {\n" +
                "            \"age\":1\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\":\"tomshidi\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        sourceJson = JsonUtil.replaceBlank(sourceJson);
        Pattern p = Pattern.compile(JsonUtil.BLANK_CHAR_PATTERN);
        Matcher m = p.matcher(sourceJson);
        assertFalse(m.matches(), "替换空白、换行符失败");
    }

    @Test
    public void testGetValueByDepthPattern() {
        String sourceJson = "{\n" +
                "    \"person\":[\n" +
                "        {\n" +
                "            \"age\":1\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\":\"tomshidi\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String depthPattern = "[person][0][age]";
        Double valueByDepthPattern = JsonUtil.getValueByDepthPattern(sourceJson, depthPattern);
        assertNotEquals(0, valueByDepthPattern.intValue());
    }

    @Test
    public void testGetDepthList() {
        String depthPattern = "[person][tomshidi][age]";
        List<String> depthList = JsonUtil.getDepthList(depthPattern);
        assertNotEquals(0, depthList.size());
    }
}