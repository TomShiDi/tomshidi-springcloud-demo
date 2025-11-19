package com.tomshidi.jpa.service;


import com.tomshidi.jpa.model.dto.ModelParamInfoDto;

import java.util.List;

/**
 * 模型参数操作
 *
 * @author tomshidi
 * @since 2024/4/17 14:24
 */
public interface ModelParamService {
    /**
     * 新增模型参数
     *
     * @param dto 待新增信息
     */
    void addParam(ModelParamInfoDto dto);

    /**
     * 批量新增模型参数
     *
     * @param dtos 模块参数列表
     */
    void addParams(List<ModelParamInfoDto> dtos);

    /**
     * 根据id删除参数配置
     *
     * @param id 数据库表id字段参数
     */
    void deleteById(String id);

    /**
     * 根据模型编号删除参数信息
     *
     * @param modelId 模型编号
     */
    void deleteByModelId(String modelId);

    /**
     * 更新参数配置
     *
     * @param modelParamInfoDto 待更新信息
     * @return 更新后的信息
     */
    ModelParamInfoDto update(ModelParamInfoDto modelParamInfoDto);

    /**
     * 根据模型编号查找参数信息
     *
     * @param modelId 模型编号
     * @return 参数信息
     */
    List<ModelParamInfoDto> findParamByModelId(String modelId);
}
