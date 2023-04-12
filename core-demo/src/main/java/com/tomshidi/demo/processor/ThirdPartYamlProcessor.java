package com.tomshidi.demo.processor;

import com.tomshidi.demo.config.YamlSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Properties;


/**
 * @author tomshidi
 * @date 2021/12/13 16:20
 */
@Component
public class ThirdPartYamlProcessor implements InstantiationAwareBeanPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        YamlSource yamlSource = beanClass.getAnnotation(YamlSource.class);
        if (yamlSource == null) {
            return null;
        }
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource(yamlSource.path()));
        Properties properties = yamlPropertiesFactoryBean.getObject();
        if (properties != null) {
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
            MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            propertySources.addFirst(new PropertiesPropertySource("yamlProperties", properties));
        }
        return null;
    }

}
