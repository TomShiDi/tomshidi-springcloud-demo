package com.tomshidi.mybatis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author tomshidi
 * @date 2023/2/8 15:41
 */
@SpringBootTest
class TreePathOperatorTest {

    @Autowired
    private TreePathOperator treePathOperator;

    @Test
    void findChildNodeIds() {
    }

    @Test
    void findClosestChildNodeIds() {
    }

    @Test
    void findClosestParentNodeId() {
    }

    @Test
    void findAllParentNodeIds() {
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
        treePathOperator.changeNodeParent("1","0");
    }

    @Test
    void deleteNode() {
        treePathOperator.deleteNode("1");
    }

    @Test
    void deleteNodeSafe() {
        treePathOperator.deleteNodeSafe("2");
    }
}