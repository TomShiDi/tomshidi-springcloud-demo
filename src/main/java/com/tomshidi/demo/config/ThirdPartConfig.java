package com.tomshidi.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tomshidi
 * @date 2021/12/13 16:18
 */
@Component
@YamlSource(path = "config/third-part.yml")
@ConfigurationProperties(prefix = "third")
public class ThirdPartConfig {
    private String name;

    private Integer age;

    private List<String> nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getNickName() {
        return nickName;
    }

    public void setNickName(List<String> nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "ThirdPartConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName=" + nickName +
                '}';
    }
}
