package com.tomshidi.aidemo.localmcpserver.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeatherService {

    @Tool(description = "根据城市名称获取天气预报")
    public String getWeather(String city) {
        // 模拟返回天气信息
        Map<String, String> map = Map.of("北京", "降雨频繁，其中今天有小雨，气温5-12℃",
                "上海", "多云转晴，气温10-18℃",
                "广州", "晴天，气温15-25℃");
        return map.getOrDefault(city, "抱歉，暂时无法获取该城市的天气信息");
    }
}
