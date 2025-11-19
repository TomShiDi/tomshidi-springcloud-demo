package com.tomshidi.jpa.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 模型信息
 * @author tomshidi
 * @since 2024/4/17 11:04
 */
@Entity(name = "model_info")
public class ModelInfo {
    @Id
    private String id;

    /**
     * 关联的目录id
     */
    @Column(name = "dic_id")
    private String dicId;

    /**
     * 模型名
     */
    @Column(name = "model_name")
    private String modelName;

    /**
     * 模型地址
     */
    @Column(name = "model_url")
    private String modelUrl;

    /**
     * 模型描述
     */
    @Column(name = "model_desc")
    private String modelDesc;

    /**
     * 数据源
     */
    @Column(name = "model_data_source")
    private String modelDataSource;

    /**
     * 算法规则文件id
     */
    @Column(name = "algorithm_desc_file_id")
    private String algorithmDescFileId;

    /**
     * 算法规则文件名
     */
    @Column(name = "algorithm_desc_file_name")
    private String algorithmDescFileName;

    /**
     * sql模板
     */
    @Column(name = "sql_template")
    private String sqlTemplate;

    @OneToMany(targetEntity = ModelParamInfo.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE)
    @JoinColumn(name = "model_id", updatable = false)
    private List<ModelParamInfo> paramInfoList;

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

    public List<ModelParamInfo> getParamInfoList() {
        return paramInfoList;
    }

    public void setParamInfoList(List<ModelParamInfo> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }
}
