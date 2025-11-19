package com.tomshidi.jpa.service;


import com.tomshidi.jpa.model.dto.ModelDicInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * 模型目录操作
 * @author tomshidi
 * @since 2024/4/17 13:46
 */
public interface ModelDicService {
    /**
     * 添加目录
     *
     * @param dicName  目录名
     * @param parentId 父目录id，为空时创建根目录
     */
    void addDic(String dicName, String parentId);

    /**
     * 根据id删除目录，需要考虑目录下数据的处理方式
     *
     * @param id 目录id
     * @return 被删除的目录编号
     */
    List<String> deleteById(String id);

    /**
     * 更新目录信息
     *
     * @param modelDicInfoDto 待更新信息
     * @return 更新后的目录信息
     */
    ModelDicInfoDto update(ModelDicInfoDto modelDicInfoDto);

    /**
     * 查找当前目录下的子目录列表信息
     *
     * @param currDicId 当前目录id
     * @return 当前目录以及子目录信息
     */
    ModelDicInfoDto findChildrenById(String currDicId);

    /**
     * 根据目录名模糊查找
     *
     * @param dicName 目录名
     * @return 目录信息列表
     */
    Page<ModelDicInfoDto> findDicByNameLike(String dicName, Integer pageIndex, Integer pageSize);

    /**
     * 查找模型管理根目录
     *
     * @return 根目录信息
     */
    List<ModelDicInfoDto> findRootDics();

    /**
     * 获取以当前节点为根的树
     *
     * @param currNodeId 当前节点
     * @return 层级树
     */
    ModelDicInfoDto findTree(String currNodeId);
}
