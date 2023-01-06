package com.tomshidi.demo.config;

import com.tomshidi.demo.factory.TomPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author tomshidi
 * @date 2022/2/17 17:07
 */
@Component
@PropertySource(value = "classpath:config/tomshidi.properties", encoding = "UTF-8", factory = TomPropertySourceFactory.class)
public class TomshidiConfig {
    private String name;

    @Value("${name}")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TomshidiConfig{" +
                "name='" + name + '\'' +
                '}';
    }

//    @Bean
//    @ConditionalOnMissingBean(name = DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME)
//    public LocaleResolver localeResolver() {
//        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
//        return localeResolver;
//    }
}
