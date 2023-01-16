package com.tomshidi.demo.config;

import com.tomshidi.demo.component.AComponentImpl;
import com.tomshidi.demo.component.BComponentImpl;
import com.tomshidi.demo.component.ComponentInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author tomshidi
 * @date 2022/7/14 14:38
 */
@Configuration
public class TestConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConfiguration.class);

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public String forBeanExecute() {
        LOGGER.info("@Bean 标注的方法被执行");
        return new String("Tomshidi");
    }

    @Bean
    @ConditionalOnMissingBean(ComponentInterface.class)
    public ComponentInterface componentInterfaceA() {
        return new AComponentImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ComponentInterface.class)
    public ComponentInterface componentInterfaceB() {
        return new BComponentImpl();
    }
}
