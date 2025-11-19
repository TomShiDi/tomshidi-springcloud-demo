package com.tomshidi.demo.utils;

import com.tomshidi.demo.threads.DemoThread;
import jakarta.servlet.http.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author tomshidi
 * @date 2022/4/13 14:05
 */
public class RequestUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(DemoThread.class);

    public static void sendRequest(Cookie[] cookies) throws IOException {
        if (cookies == null) {
            return;
        }
        HttpClient httpClient = new HttpClient();
        HttpMethod postMethod = new PostMethod("https://www.baidu.com");
        LOGGER.info("添加cookie，数量是：{}", cookies.length);
        for (Cookie cookie : cookies) {
            postMethod.addRequestHeader("Cookie", String.format("%s=%s", cookie.getName(), cookie.getValue()));
        }
//        LOGGER.info("请求结果：{}", httpClient.executeMethod(postMethod));
    }
}