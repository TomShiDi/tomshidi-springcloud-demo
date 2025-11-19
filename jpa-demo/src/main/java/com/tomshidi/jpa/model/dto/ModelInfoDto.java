package com.tomshidi.jpa.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 模型信息
 *
 * @author tomshidi
 * @since 2024/4/17 11:04
 */
@Schema(title = "模型信息")
public class ModelInfoDto {
    @Schema(description = "新增时不需要传值")
    private String id;

    /**
     * 关联的目录id
     */
    @Schema(description = "关联的目录id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dicId;

    /**
     * 目录名
     */
    @Schema(description = "目录名", accessMode = Schema.AccessMode.READ_ONLY)
    private String dicName;

    /**
     * 模型名
     */
    @Schema(description = "模型名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modelName;

    /**
     * 模型地址
     */
    @Schema(description = "模型地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modelUrl;

    /**
     * 模型描述
     */
    @Schema(description = "模型描述", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modelDesc;

    /**
     * 数据源
     */
    @Schema(description = "模型数据源", requiredMode = Schema.RequiredMode.REQUIRED)
    private String modelDataSource;

    /**
     * 算法规则文件id
     */
    @Schema(description = "算法规则文件id，从上传接口获取", requiredMode = Schema.RequiredMode.REQUIRED)
    private String algorithmDescFileId;

    /**
     * 算法规则文件名
     */
    @Schema(description = "算法规则文件名，从上传接口获取", requiredMode = Schema.RequiredMode.REQUIRED)
    private String algorithmDescFileName;

    @Schema(description = "sql模板")
    private String sqlTemplate;

    @Schema(description = "模型参数")
    private List<ModelParamInfoDto> paramInfoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public String getModelDataSource() {
        return modelDataSource;
    }

    public void setModelDataSource(String modelDataSource) {
        this.modelDataSource = modelDataSource;
    }

    public String getAlgorithmDescFileId() {
        return algorithmDescFileId;
    }

    public void setAlgorithmDescFileId(String algorithmDescFileId) {
        this.algorithmDescFileId = algorithmDescFileId;
    }

    public String getAlgorithmDescFileName() {
        return algorithmDescFileName;
    }

    public void setAlgorithmDescFileName(String algorithmDescFileName) {
        this.algorithmDescFileName = algorithmDescFileName;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }

    public void setSqlTemplate(String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public List<ModelParamInfoDto> getParamInfoList() {
        return paramInfoList;
    }

    public void setParamInfoList(List<ModelParamInfoDto> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }
}
