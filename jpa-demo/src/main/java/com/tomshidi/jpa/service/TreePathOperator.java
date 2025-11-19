package com.tomshidi.jpa.service;


import java.util.List;

/**
 * 节点路径信息操作
 * @author tomshidi
 * @since 2024-4-16 11:18:05
 */
public interface TreePathOperator {

    /**
     * 查找所有的子节点
     * @param currNodeId 当前节点
     * @return 子节点列表
     */
    List<String> findChildNodeIds(String currNodeId);

    /**
     * 查找最近一级的子节点列表
     * @param currNodeId 当前节点
     * @return 最近一级子节点列表
     */
    List<String> findClosestChildNodeIds(String currNodeId);

    /**
     * 查找最近的父节点
     * @param currNodeId 当前节点
     * @return 最近的父节点
     */
    String findClosestParentNodeId(String currNodeId);

    /**
     * 查找所有父节点id
     * @param currNodeId 当前节点
     * @return 父节点id列表
     */
    List<String> findAllParentNodeIds(String currNodeId);

    /**
     * 查找指定业务的所有根节点
     *
     * @param type 业务类型
     * @return 根节点id
     */
    List<String> findRootNodeIds(String type);

    /**
     * 在指定父节点下新增一个节点
     *
     * @param nodeId       待新增节点id
     * @param type 业务类型
     * @see com.dist.xdata.pdg.common.constant.ModuleCode
     * @param parentNodeId 父节点id
     */
    void addNode(String nodeId, String type, String parentNodeId);

    /**
     * 新增根目录
     *
     * @param nodeId 目录id
     */
    void addNode(String nodeId, String type);

    /**
     * 移动节点
     * @param nodeId 当前节点id
     * @param newParentNodeId 新的父节点id
     */
    void changeNodeParent(String nodeId, String newParentNodeId);

    /**
     * 删除节点，并删除其子节点
     * @param nodeId 节点id
     * @return 被删除的节点id
     */
    List<String> deleteNode(String nodeId);

    /**
     * 删除节点，但保留子节点
     * @param nodeId 节点id
     */
    void deleteNodeSafe(String nodeId);

    /**
     * 查找当前节点的所有子节点，同一层的节点位于同一个list中
     * @param currNodeId 当前节点
     * @return list的第一个元素为当前节点，第二个元素为第一层子节点id列表，第三个元素为第二层子节点id列表，以此类推
     */
    List<List<String>> findLevelChildren(String currNodeId);
}
