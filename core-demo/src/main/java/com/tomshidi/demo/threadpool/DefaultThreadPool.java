package com.tomshidi.demo.threadpool;

import com.tomshidi.demo.proxy.CgProxyFactory;
import com.tomshidi.demo.threads.ExtraParam;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tomshidi
 * @date 2022/3/22 10:59
 */
public class DefaultThreadPool {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultThreadPool.class);

    private DefaultThreadPool() {
    }

    private static final class RequestContextHolder {
        static final Map<Runnable, Cookie[]> COOKIE_MAP = new ConcurrentHashMap<>(16);
    }


    private static final class InstanceHolder {
        static Executor INSTANCE = new ThreadPoolExecutor(1000,
                1000,
                20000,
                TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(10000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        LOGGER.info("**** 线程工厂方法执行 ****");
                        return new Thread(null, r, r.toString());
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());
    }

    public static Executor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static void execute(Runnable runnable) throws InterruptedException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        Cookie[] cookies;
        if (attributes != null && runnable instanceof ExtraParam) {
            cookies = attributes.getRequest().getCookies();
//            if (cookies != null && cookies.length > 0) {
//                runnable = (Runnable) CgProxyFactory.enhance(runnable.getClass());
//                ((ExtraParam) runnable).setParam(HttpHeaders.COOKIE, cookies);
//            }
            runnable = (Runnable) CgProxyFactory.enhance(runnable.getClass());
            ((ExtraParam) runnable).setParam(HttpHeaders.COOKIE, cookies);
        }
        InstanceHolder.INSTANCE.execute(runnable);
    }

    public static void shutdown() {
        if (InstanceHolder.INSTANCE == null) {
            return;
        }
        ((ThreadPoolExecutor) InstanceHolder.INSTANCE).shutdownNow();
        InstanceHolder.INSTANCE = null;
    }
}
