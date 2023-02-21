package com.tomshidi.mybatis.mapper;

import com.tomshidi.model.TreePathTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tomshidi
 * @date 2023/2/8 13:47
 */
@Mapper
public interface TreePathTableMapper {

    void insert(@Param("treePathTable") TreePathTable treePathTable);

    void insertMore(@Param("list") List<TreePathTable> list);

    /**
     * 更新
     * @param descendantList
     * @param ancestorList
     */
    void reduceOneDistance(@Param("descendantList") List<String> descendantList, @Param("ancestorList") List<String> ancestorList);

    /**
     * 根据当前节点删除路径信息
     * @param descendantList 当前节点列表
     */
    void deleteByDescendants(@Param("descendantList") List<String> descendantList);

    /**
     * 根据祖先节点删除路径信息
     * @param ancestorList 祖先节点列表
     */
    void deleteByAncestors(@Param("ancestorList") List<String> ancestorList);

    /**
     * 查找所有子节点
     * @param currNodeId 当前节点
     * @return 子节点id列表
     */
    List<String> findChildNodeIds(@Param("currNodeId") String currNodeId);

    /**
     * 查找所有子节点到当前节点的路径信息
     * @param currNodeId 当前节点
     * @return 路径信息列表
     */
    List<TreePathTable> findChildTreePath(@Param("currNodeId") String currNodeId);

    /**
     * 根据距离查找子节点id
     * @param currNodeId 当前节点
     * @param distance 距离
     * @return 子节点id列表
     */
    List<String> findChildNodeIdByDistance(@Param("currNodeId") String currNodeId, @Param("distance") Integer distance);

    /**
     * 查找最近一级的所有子节点id列表
     * @param currNodeId 当前节点
     * @return 子节点id列表
     */
    List<String> findClosestChildNodeIds(@Param("currNodeId") String currNodeId);

    /**
     * 查找最近一级的父节点id
     * @param currNodeId 当前节点
     * @return 最近一级父节点id
     */
    String findClosestParentNodeId(@Param("currNodeId") String currNodeId);

    /**
     * 查找所有父节点id，返回的列表元素按照由远及近排列
     * 例如：6节点的父节点是3，3节点的父节点是1，1节点的父节点是0
     * 那么6节点调用该方法返回的列表即为：[0,1,3]
     * @param currNodeId 当前节点
     * @return 父节点id列表
     */
    List<String> findAllParentNodeIds(@Param("currNodeId") String currNodeId);

    /**
     * 查找所有当前节点到父节点的路径信息
     * @param currNodeId 当前节点
     * @return 路径信息
     */
    List<TreePathTable> findAllParentTreePath(@Param("currNodeId") String currNodeId);
}
