package com.tomshidi.demo.threads;

import com.tomshidi.demo.proxy.CgProxyFactory;
import com.tomshidi.demo.utils.RequestUtil;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

/**
 * @author tomshidi
 * @date 2022/3/22 10:25
 */
public class DemoThread extends ExtraParam implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(DemoThread.class);

    public DemoThread(String s1, int i) {
    }

    @Override
    public void run() {
        LOGGER.info("run方法执行");
        LOGGER.info(Thread.currentThread().getName());
        Cookie[] cookies = CgProxyFactory.cookieThreadLocal.get();
        CgProxyFactory.cookieThreadLocal.remove();
//        if (cookies == null || cookies.length <= 0) {
//            System.exit(1);
//        }
        removeParam(HttpHeaders.COOKIE);
//        if (cookies != removeParam(HttpHeaders.COOKIE)) {
//            System.exit(1);
//        }
        try {
            RequestUtil.sendRequest(cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        TestConfig testConfig = ApplicationContextHolder.getApplicationContext().getBean(TestConfig.class);
//        testConfig.logCookie();
//        Cookie[] cookies = (Cookie[]) this.getParam(HttpHeaders.COOKIE);
//        if (cookies == null) {
//            return;
//        }
//        for (Cookie cookie : cookies) {
//            LOGGER.info("【{}】 {}={}", Thread.currentThread().getName(), cookie.getName(), cookie.getValue());
//        }
    }

}
