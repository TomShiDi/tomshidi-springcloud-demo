package com.tomshidi.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tomshidi.base.constant.SymbolConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 轻量级快捷解析json数据
 * @author tomshidi
 * @date 2022/7/4 16:32
 */
public class JsonUtil {

    public static final String DEPTH_PATTERN = "(?<outer>\\[(?<inner>\\w+)]).*";

    public static final String BLANK_CHAR_PATTERN = "\\s*|\t|\r|\n";

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * 剔除字符串中的空白字符、换行
     *
     * @param str 待处理字符串
     * @return 剔除空白字符、换行的字符串
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile(BLANK_CHAR_PATTERN);
            Matcher m = p.matcher(str);
            dest = m.replaceAll(SymbolConstants.EMPTY);
        }
        return dest;
    }


    /**
     * 像python、js那样获取json字符串中某个key对应的值
     *
     * @param sourceJson         原始json字符串
     * @param depthPattern       层级深度表达式
     *                           例如：[person][tomshidi][age]
     *                           支持数组获取：[person][0][age]
     * @param <T>                返回值泛型
     * @return 指定深度key对应的value
     */
    public static <T> T getValueByDepthPattern(String sourceJson, String depthPattern) {
        // 替换json字符串中的空白字符以及换行符，本方案可不做；
//        sourceJson = replaceBlank(sourceJson);
        Object jsonObject = GSON.fromJson(sourceJson, new TypeToken<Map<String, Object>>() {
        }.getType());
        return getValueByDepthPattern(jsonObject, depthPattern);
    }

    /**
     * 像python、js那样获取json字符串中某个key对应的值
     *
     * @param sourceJson            json数据解析出来的map对象
     * @param depthPattern       层级深度表达式
     *                           例如：[person][tomshidi][age]
     *                           支持数组获取：[person][0][age]
     * @param <T>                返回值泛型
     * @return 指定深度key对应的value
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueByDepthPattern(Object sourceJson, String depthPattern) {
        List<String> depthList = getDepthList(depthPattern);
        if (depthList.isEmpty()) {
            return null;
        }
        Object jsonObject = sourceJson;
        for (String depthName : depthList) {
            if (jsonObject instanceof Map) {
                jsonObject = ((Map<String, Object>) jsonObject).get(depthName);
            } else if (jsonObject instanceof List) {
                jsonObject = ((List<Object>) jsonObject).get(Double.valueOf(depthName).intValue());
            }
        }
        return (T) jsonObject;
    }

    /**
     * 获取层级深度名列表
     *
     * @param depthPattern 层级深度表达式
     *                     例如：[person][tomshidi][age]
     *                     支持数组获取：[person][0][age]
     * @return 层级名列表，例如：[person,tomshidi,age] 或者[person,0,age]
     */
    public static List<String> getDepthList(String depthPattern) {
        Matcher matcher;
        List<String> depthList = new ArrayList<>();
        Pattern pattern = Pattern.compile(DEPTH_PATTERN);
        while (depthPattern != null && !depthPattern.isEmpty()) {
            if (!depthPattern.startsWith(SymbolConstants.BRACKET_START) || !depthPattern.endsWith(SymbolConstants.BRACKET_END)) {
                throw new RuntimeException("规则模板不合法，请检查开始符'['与结束符']'数量是否匹配");
            }
            matcher = pattern.matcher(depthPattern);
            if (matcher.matches()) {
                depthList.add(matcher.group("inner"));
                depthPattern = depthPattern.replace(matcher.group("outer"), SymbolConstants.EMPTY);
            }
        }
        return depthList;
    }
}
