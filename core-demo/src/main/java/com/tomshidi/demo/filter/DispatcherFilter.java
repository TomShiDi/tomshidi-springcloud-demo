package com.tomshidi.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author tomshidi
 * @date 2021/10/11 15:38
 */
// @WebFilter 的asyncSupported参数默认值是false，会导致异步请求失败
//@WebFilter(urlPatterns = {"*"}, asyncSupported = true)
@Component
public class DispatcherFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(DispatcherFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("执行过滤器逻辑：{}", servletRequest.getLocalAddr());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
