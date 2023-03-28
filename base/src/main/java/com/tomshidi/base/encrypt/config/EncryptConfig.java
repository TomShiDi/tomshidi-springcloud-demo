package com.tomshidi.base.encrypt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tomshidi
 * @since 2023/3/24 14:39
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "application.encrypt")
public class EncryptConfig {

    /**
     * key是加密算法名，与EncryptEnum中的枚举code保持一致
     * value是配置项
     */
    private Map<String, Map<String, String>> algorithmAndProperty;

    /**
     * key是实体类全路径名
     * value是实体类中加密处理的成员变量列表
     */
    private Map<String, List<String>> affectedEntityFields;

    /**
     * 获取算法配置项
     *
     * @param algorithmName 加密算法名
     * @return 算法配置项
     */
    public Map<String, String> getAlgorithmProperty(String algorithmName) {
        if (ObjectUtils.isEmpty(algorithmAndProperty)) {
            return new HashMap<>(0);
        }
        return algorithmAndProperty.get(algorithmName);
    }

    public boolean needEncrypt(Class<?> type) {
        if (ObjectUtils.isEmpty(affectedEntityFields)) {
            return false;
        }
        List<String> fieldList = affectedEntityFields.get(type.getTypeName());
        return !ObjectUtils.isEmpty(fieldList);
    }

    public boolean needEncrypt(Class<?> type, String fieldName) {
        if (ObjectUtils.isEmpty(affectedEntityFields)) {
            return false;
        }
        List<String> fieldList = affectedEntityFields.get(type.getTypeName());
        if (ObjectUtils.isEmpty(fieldList)) {
            return false;
        }
        return fieldList.contains(fieldName);
    }

    public Map<String, Map<String, String>> getAlgorithmAndProperty() {
        return algorithmAndProperty;
    }

    public void setAlgorithmAndProperty(Map<String, Map<String, String>> algorithmAndProperty) {
        this.algorithmAndProperty = algorithmAndProperty;
    }

    public Map<String, List<String>> getAffectedEntityFields() {
        return affectedEntityFields;
    }

    public void setAffectedEntityFields(Map<String, List<String>> affectedEntityFields) {
        this.affectedEntityFields = affectedEntityFields;
    }

}
