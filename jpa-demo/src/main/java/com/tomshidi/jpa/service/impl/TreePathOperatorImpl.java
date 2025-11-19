package com.tomshidi.jpa.service.impl;


import com.tomshidi.jpa.entity.TreePathTable;
import com.tomshidi.jpa.repository.TreePathTableRepo;
import com.tomshidi.jpa.service.TreePathOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tomshidi
 * @date 2023/2/8 14:51
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class TreePathOperatorImpl implements TreePathOperator {

    private TreePathTableRepo treePathTableRepo;

    @Autowired
    public void setTreePathTableRepo(TreePathTableRepo treePathTableRepo) {
        this.treePathTableRepo = treePathTableRepo;
    }

    @Override
    public List<String> findChildNodeIds(String currNodeId) {
        return treePathTableRepo.findChildNodeIds(currNodeId);
    }

    @Override
    public List<String> findClosestChildNodeIds(String currNodeId) {
        return treePathTableRepo.findClosestChildNodeIds(currNodeId);
    }

    @Override
    public String findClosestParentNodeId(String currNodeId) {
        return treePathTableRepo.findClosestParentNodeId(currNodeId);
    }

    @Override
    public List<String> findAllParentNodeIds(String currNodeId) {
        return treePathTableRepo.findAllParentNodeIds(currNodeId);
    }

    @Override
    public List<String> findRootNodeIds(String type) {
        return treePathTableRepo.findRootNodeIds(type);
    }

    @Override
    public void addNode(String nodeId, String type) {
        addNode(nodeId, type, "");
    }

    @Override
    public void addNode(String nodeId, String type, String parentNodeId) {
        List<TreePathTable> parentTreePathList = treePathTableRepo.findAllParentTreePath(parentNodeId);
        List<TreePathTable> treePathTableList = new ArrayList<>(parentTreePathList.size() + 1);
        TreePathTable treePathTable;
        for (TreePathTable t : parentTreePathList) {
            treePathTable = new TreePathTable()
                    .setAncestor(t.getAncestor())
                    .setDescendant(nodeId)
                    .setDistance(t.getDistance() + 1)
                    .setType(type);
            treePathTableList.add(treePathTable);
        }
        treePathTable = new TreePathTable()
                .setAncestor(nodeId)
                .setDescendant(nodeId)
                .setDistance(0)
                .setType(type);
        treePathTableList.add(treePathTable);
        treePathTableRepo.saveAll(treePathTableList);
    }

    @Override
    public void changeNodeParent(String nodeId, String newParentNodeId) {
        String closestParentNodeId = treePathTableRepo.findClosestParentNodeId(nodeId);
        // 判断节点是否有移动
        if (newParentNodeId.equals(closestParentNodeId)) {
            return;
        }
        Map<String, Object> childTree = new HashMap<>(2);
        // 临时保存子节点层级结构
        buildChildTree(nodeId, childTree);
        List<String> allChildIdList = treePathTableRepo.findChildNodeIds(nodeId);
        // 删除所有子节点路径数据
        treePathTableRepo.deleteByDescendants(allChildIdList);
        // 重新将子节点添加到新的父节点上
        reInsertPreChildNode(childTree, newParentNodeId);
    }

    /**
     * 重新添加树形层级记录
     * @param childTree 子树数据
     * @param newParentNodeId 子树要添加到的父节点
     */
    private void reInsertPreChildNode(Map<String, Object> childTree, String newParentNodeId) {
        for (Map.Entry<String, Object> entry : childTree.entrySet()) {
            addNode(entry.getKey(), newParentNodeId);
            if (entry.getValue() != null && entry.getValue() instanceof Map) {
                reInsertPreChildNode((Map<String, Object>) entry.getValue(), entry.getKey());
            }
        }
    }

    /**
     * 构造节点树形结构
     *
     * @param currNode 当前节点
     */
    private void buildChildTree(String currNode, Map<String, Object> resultMap) {
        List<String> closestChildNodeIds = treePathTableRepo.findClosestChildNodeIds(currNode);
        if (!ObjectUtils.isEmpty(closestChildNodeIds)) {
            Map<String, Object> childMap = new HashMap<>(2);
            resultMap.put(currNode, childMap);
            for (String childId : closestChildNodeIds) {
                buildChildTree(childId, childMap);
            }
        } else {
            resultMap.put(currNode, null);
        }
    }

    @Override
    public List<String> deleteNode(String nodeId) {
        List<String> childNodeIdList = treePathTableRepo.findChildNodeIds(nodeId);
        childNodeIdList.add(nodeId);
        treePathTableRepo.deleteByDescendants(childNodeIdList);
        return childNodeIdList;
    }

    @Override
    public void deleteNodeSafe(String nodeId) {
        List<String> parentNodeIdList = treePathTableRepo.findAllParentNodeIds(nodeId);
        List<String> childNodeIdList = treePathTableRepo.findChildNodeIds(nodeId);
        for (String childNodeId : childNodeIdList) {
            if (!ObjectUtils.isEmpty(childNodeIdList)) {
                // 子节点保留，并移动节点位置
                treePathTableRepo.reduceOneDistance(childNodeId, parentNodeIdList);
            }
        }
        // 删除当前节点到父节点的路径
        treePathTableRepo.deleteByDescendants(Collections.singletonList(nodeId));
        // 删除当前节点到子节点的路径
        treePathTableRepo.deleteByAncestors(Collections.singletonList(nodeId));
    }

    @Override
    public List<List<String>> findLevelChildren(String currNodeId) {
        List<String> idStrList = treePathTableRepo.findLevelChildren(currNodeId);
        List<List<String>> resultList = new ArrayList<>();
        if (idStrList == null) {
            return resultList;
        }
        return idStrList.stream()
                .map(item -> Stream.of(item.split(",")).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
