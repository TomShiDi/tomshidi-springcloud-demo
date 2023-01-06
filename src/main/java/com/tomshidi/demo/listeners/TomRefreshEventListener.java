//package com.example.demo.listeners;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.refresh.ContextRefresher;
//import org.springframework.cloud.endpoint.event.RefreshEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.core.Ordered;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
///**
// * @author tomshidi
// * @date 2022/12/29 10:47
// */
//public class TomRefreshEventListener implements EnvironmentAware, ApplicationListener<RefreshEvent>, Ordered {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(TomRefreshEventListener.class);
//
//    private static final String TARGET_PROPERTY_KEY = "test-config.name-split";
//
//    private Environment environment;
//
//    private ContextRefresher refresh;
//
//    @Autowired
//    public void setRefresh(ContextRefresher refresh) {
//        this.refresh = refresh;
//    }
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Override
//    public void onApplicationEvent(RefreshEvent event) {
//        Set<String> refreshedSet = refresh.refresh();
//        if (refreshedSet.contains(TARGET_PROPERTY_KEY)) {
//            LOGGER.info("目标配置项被刷新，刷新后值为：{}", environment.getProperty(TARGET_PROPERTY_KEY));
//        }
//    }
//
//    @Override
//    public int getOrder() {
//        return Ordered.LOWEST_PRECEDENCE - 100;
//    }
//}
