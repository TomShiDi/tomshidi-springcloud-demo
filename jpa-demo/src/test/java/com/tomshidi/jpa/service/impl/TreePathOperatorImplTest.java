package com.tomshidi.jpa.service.impl;

import com.tomshidi.jpa.service.TreePathOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * 
 * @author tomshidi
 * @since 2024/4/24 17:47
 */
@SpringBootTest
class TreePathOperatorImplTest {

    @Autowired
    private TreePathOperator treePathOperator;

    @Test
    void findChildNodeIds() {
        List<String> childNodeIds = treePathOperator.findChildNodeIds("0");
        System.out.println(childNodeIds);
    }

    @Test
    void findClosestChildNodeIds() {
        List<String> closestChildNodeIds = treePathOperator.findClosestChildNodeIds("cb7fd56c-190a-4dbc-b447-98f2dfc2980a");
        System.out.println(closestChildNodeIds);
    }

    @Test
    void findClosestParentNodeId() {
        String closestParentId = treePathOperator.findClosestParentNodeId("2");
        System.out.println(closestParentId);
    }

    @Test
    void findAllParentNodeIds() {
        List<String> parentNodeIds = treePathOperator.findAllParentNodeIds("5");
        System.out.println(parentNodeIds);
    }

    @Test
    void addNode() {
        treePathOperator.addNode("0", "0");
        treePathOperator.addNode("1", "0");
        treePathOperator.addNode("2", "1");
        treePathOperator.addNode("3", "1");
        treePathOperator.addNode("4", "0");
        treePathOperator.addNode("5", "3");
        treePathOperator.addNode("6", "3");
    }

    @Test
    void changeNodeParent() {
        treePathOperator.changeNodeParent("3","0");
    }

    @Test
    void deleteNode() {
        treePathOperator.deleteNode("1");
    }

    @Test
    void deleteNodeSafe() {
        treePathOperator.deleteNodeSafe("3");
    }
}