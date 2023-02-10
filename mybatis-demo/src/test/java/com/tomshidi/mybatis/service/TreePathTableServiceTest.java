package com.tomshidi.mybatis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author tomshidi
 * @date 2023/2/8 15:41
 */
@SpringBootTest
class TreePathTableServiceTest {

    @Autowired
    private TreePathTableService treePathTableService;

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
        treePathTableService.addNode("0", "0");
        treePathTableService.addNode("1", "0");
        treePathTableService.addNode("2", "1");
        treePathTableService.addNode("3", "1");
        treePathTableService.addNode("4", "0");
        treePathTableService.addNode("5", "3");
        treePathTableService.addNode("6", "3");
    }

    @Test
    void changeNodeParent() {
        treePathTableService.changeNodeParent("1","0");
    }

    @Test
    void deleteNode() {
        treePathTableService.deleteNode("1");
    }

    @Test
    void deleteNodeSafe() {
        treePathTableService.deleteNodeSafe("2");
    }
}