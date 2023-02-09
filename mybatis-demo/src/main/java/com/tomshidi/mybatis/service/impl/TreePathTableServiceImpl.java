package com.tomshidi.mybatis.service.impl;


import com.tomshidi.model.TreePathTable;
import com.tomshidi.mybatis.mapper.TreePathTableMapper;
import com.tomshidi.mybatis.service.TreePathTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tomshidi
 * @date 2023/2/8 14:51
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class TreePathTableServiceImpl implements TreePathTableService {

    private TreePathTableMapper treePathTableMapper;

    @Autowired
    public void setTreePathTableMapper(TreePathTableMapper treePathTableMapper) {
        this.treePathTableMapper = treePathTableMapper;
    }

    @Override
    public List<String> findChildNodeIds(String currNodeId) {
        return treePathTableMapper.findChildNodeIds(currNodeId);
    }

    @Override
    public List<String> findClosestChildNodeIds(String currNodeId) {
        return treePathTableMapper.findClosestChildNodeIds(currNodeId);
    }

    @Override
    public String findClosestParentNodeId(String currNodeId) {
        return treePathTableMapper.findClosestParentNodeId(currNodeId);
    }

    @Override
    public List<String> findAllParentNodeIds(String currNodeId) {
        return treePathTableMapper.findAllParentNodeIds(currNodeId);
    }

    @Override
    public void addNode(String nodeId, String parentNodeId) {
        List<TreePathTable> parentTreePathList = treePathTableMapper.findAllParentTreePath(parentNodeId);
        List<TreePathTable> treePathTableList = new ArrayList<>(parentTreePathList.size() + 1);
        TreePathTable treePathTable;
        for (TreePathTable t : parentTreePathList) {
            treePathTable = new TreePathTable();
            treePathTable.setAncestor(t.getAncestor());
            treePathTable.setDescendant(nodeId);
            treePathTable.setDistance(t.getDistance() + 1);
            treePathTableList.add(treePathTable);
        }
        treePathTable = new TreePathTable();
        treePathTable.setAncestor(nodeId);
        treePathTable.setDescendant(nodeId);
        treePathTable.setDistance(0);
        treePathTableList.add(treePathTable);
        treePathTableMapper.insertMore(treePathTableList);
    }

    @Override
    public void changeNodeParent(String nodeId, String newParentNodeId) {
        String closestParentNodeId = treePathTableMapper.findClosestParentNodeId(nodeId);
        // 判断节点是否有移动
        if (newParentNodeId.equals(closestParentNodeId)) {
            return;
        }
        Map<String, Object> childTree = new HashMap<>(2);
        buildChildTree(nodeId, childTree);
        List<String> allChildIdList = treePathTableMapper.findChildNodeIds(nodeId);
        treePathTableMapper.deleteByDescendants(allChildIdList);
        reInsertPreChildNode(childTree, newParentNodeId);
    }

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
     * @return 树状map
     */
    private void buildChildTree(String currNode, Map<String, Object> resultMap) {
        List<String> closestChildNodeIds = treePathTableMapper.findClosestChildNodeIds(currNode);
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
    public void deleteNode(String nodeId) {
        List<String> childNodeIdList = treePathTableMapper.findChildNodeIds(nodeId);
        childNodeIdList.add(nodeId);
        treePathTableMapper.deleteByDescendants(childNodeIdList);
    }

    @Override
    public void deleteNodeSafe(String nodeId) {
        List<String> parentNodeIdList = treePathTableMapper.findAllParentNodeIds(nodeId);
        List<String> childNodeIdList = treePathTableMapper.findChildNodeIds(nodeId);
        if (!ObjectUtils.isEmpty(childNodeIdList)) {
            // 子节点保留，并移动节点位置
            treePathTableMapper.reduceOneDistance(childNodeIdList, parentNodeIdList);
        }
        // 删除当前节点到父节点的路径
        treePathTableMapper.deleteByDescendants(Collections.singletonList(nodeId));
        // 删除当前节点到子节点的路径
        treePathTableMapper.deleteByAncestors(Collections.singletonList(nodeId));
    }
}
