package com.tomshidi.jpa.service;

import com.tomshidi.jpa.model.dto.ModelInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 模型信息操作
 *
 * @author tomshidi
 * @since 2024/4/17 14:15
 */
public interface ModelInfoService {
    /**
     * 新增模型信息
     *
     * @param modelInfoDto 待新增信息
     */
    void addModelInfo(ModelInfoDto modelInfoDto);

    /**
     * 根据id删除模型信息
     *
     * @param id 模型编号
     */
    void deleteById(String id);

    /**
     * 删除目录下所有的模型数据
     *
     * @param dicIds 目录id列表
     */
    void deleteByDicIds(List<String> dicIds);

    /**
     * 更新模型信息
     *
     * @param modelInfoDto 待更新信息
     * @return 更新后的信息
     */
    ModelInfoDto update(ModelInfoDto modelInfoDto);

    /**
     * 查找某目录下的模型信息
     * @param dicId     目录id
     * @param pageIndex 页码
     * @param pageSize  分页大小
     * @return 分页模型信息
     */
    Page<ModelInfoDto> findModelByDicId(String dicId, Integer pageIndex, Integer pageSize);

    /**
     * 根据模型名模糊查找
     *
     * @param name 模型名关键字
     * @return 模型信息
     */
    List<ModelInfoDto> findModelByName(String name);
}
