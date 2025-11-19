package com.tomshidi.demo.proxy;

import com.tomshidi.demo.threads.ExtraParam;
import jakarta.servlet.http.Cookie;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.lang.reflect.Method;

/**
 * @author tomshidi
 * @date 2022/3/22 10:28
 */
public class CgProxyFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CgProxyFactory.class);
    public static final ThreadLocal<Cookie[]> cookieThreadLocal =  new ThreadLocal<>();

    public static Object enhance(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CglibProxy());
        Class<?>[] parameterTypes = clazz.getConstructors()[0].getParameterTypes();
        return enhancer.create(parameterTypes, new Object[parameterTypes.length]);
    }

    static class CglibProxy implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            if ("run".equals(method.getName())) {
                Cookie[] cookies = (Cookie[]) ((ExtraParam) o).getParam(HttpHeaders.COOKIE);
                if (cookies != null && cookies.length > 0) {
                    LOGGER.info(Thread.currentThread().getName());
                    cookieThreadLocal.remove();
                    cookieThreadLocal.set(cookies);
                }
            }
            return methodProxy.invokeSuper(o, objects);
        }
    }
}
