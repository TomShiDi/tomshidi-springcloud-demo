package com.tomshidi.mybatis.service;


import java.util.List;

/**
 * @author tomshidi
 * @date 2023/2/8 14:45
 */
public interface TreePathTableService {

    List<String> findChildNodeIds(String currNodeId);

    List<String> findClosestChildNodeIds(String currNodeId);

    String findClosestParentNodeId(String currNodeId);

    /**
     * 查找所有父节点id
     * @param currNodeId 当前节点
     * @return 父节点id列表
     */
    List<String> findAllParentNodeIds(String currNodeId);

    /**
     * 在指定父节点下新增一个节点
     * @param nodeId 待新增节点id
     * @param parentNodeId 父节点id
     */
    void addNode(String nodeId, String parentNodeId);

    /**
     * 移动节点
     * @param nodeId 当前节点id
     * @param newParentNodeId 新的父节点id
     */
    void changeNodeParent(String nodeId, String newParentNodeId);

    /**
     * 删除节点，并删除其子节点
     * @param nodeId 节点id
     */
    void deleteNode(String nodeId);

    /**
     * 删除节点，但保留子节点
     * @param nodeId 节点id
     */
    void deleteNodeSafe(String nodeId);
}
