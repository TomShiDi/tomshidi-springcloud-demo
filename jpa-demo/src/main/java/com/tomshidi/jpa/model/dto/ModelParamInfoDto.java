package com.tomshidi.jpa.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 模型参数信息
 * @author tomshidi
 * @since 2024/4/17 11:12
 */
@Schema(title = "模型参数")
public class ModelParamInfoDto {
    @Schema(description = "新增数据时不需要传值")
    private String id;

    /**
     * 模型输入参数id
     */
    @Schema(description = "参数编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paramId;

    /**
     * 参数名
     */
    @Schema(description = "参数名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paramName;

    /**
     * 参数值
     */
    @Schema(description = "参数值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String paramValue;

    /**
     * 参数默认值
     */
    @Schema(description = "参数默认值")
    private String paramDefaultValue;

    /**
     * 关联的模型id
     */
    @Schema(description = "关联的模型id", requiredMode = Schema.RequiredMode.REQUIRED)
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
