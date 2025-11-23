package com.tomshidi.aidemo.toolcalling.tools;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;

public class DateTimeTools {

    /**
     * 获取当前的日期和时间
     * returnDirect 设置为 false，表示需要经过大模型处理，而不是直接返回给用户
     * @return 当前的日期和时间字符串
     */
    @Tool(description = "获取当前的日期和时间", returnDirect = false)
    public String getCurrentDateTime() {
        return LocalDateTime.now().toString();
    }
}
