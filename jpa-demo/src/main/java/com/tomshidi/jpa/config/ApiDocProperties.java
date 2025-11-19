package com.tomshidi.jpa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tomshidi
 * @since 2024/4/24 17:58
 */
@ConfigurationProperties(prefix = "dist.api.doc")
public class ApiDocProperties {
    /**
     * 是否对外暴露接口文档
     */
    private boolean enable = true;

    /**
     * 接口文档名称
     */
    private String name = "TOMSHIDI";

    /**
     * 接口文档描述
     */
    private String description = "接口文档";

    /**
     * 接口文档参考地址
     */
    private String website = "none";

    /**
     * 项目负责人邮箱
     */
    private String author = "1341109792@qq.com";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
