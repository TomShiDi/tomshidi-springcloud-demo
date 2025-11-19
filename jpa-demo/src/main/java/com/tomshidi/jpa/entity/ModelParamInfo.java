package com.tomshidi.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * 模型参数信息
 * @author tomshidi
 * @since 2024/4/17 11:12
 */
@Entity(name = "model_param_info")
public class ModelParamInfo {
    @Id
    private String id;

    /**
     * 模型输入参数id
     */
    @Column(name = "param_id")
    private String paramId;

    /**
     * 参数名
     */
    @Column(name = "param_name")
    private String paramName;

    /**
     * 参数值
     */
    @Column(name = "param_value")
    private String paramValue;

    /**
     * 参数默认值
     */
    @Column(name = "param_default_value")
    private String paramDefaultValue;

    /**
     * 关联的模型id
     */
    @Column(name = "model_id")
    private String modelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamDefaultValue() {
        return paramDefaultValue;
    }

    public void setParamDefaultValue(String paramDefaultValue) {
        this.paramDefaultValue = paramDefaultValue;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
