package com.tomshidi.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author tomshidi
 * @date 2021/10/11 15:38
 */
@WebFilter(urlPatterns = {"*"})
//@Component
public class DispatcherFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(DispatcherFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("执行过滤器逻辑：{}", servletRequest.getLocalAddr());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
