package com.tomshidi.mybatis.mapper;

import com.tomshidi.model.TreePathTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomshidi
 * @date 2023/2/8 14:08
 */
@SpringBootTest
class TreePathTableMapperTest {

    @Autowired
    private TreePathTableMapper treePathTableMapper;

    @Test
    void insert() {
        TreePathTable treePathTable = new TreePathTable();
        treePathTable.setAncestor("0");
        treePathTable.setDescendant("0");
        treePathTable.setDistance(0);
        treePathTableMapper.insert(treePathTable);
    }

    @Test
    void insertMore() {
        List<TreePathTable> list = new ArrayList<>(4);
        TreePathTable treePathTable1 = new TreePathTable();
        treePathTable1.setAncestor("0");
        treePathTable1.setDescendant("1");
        treePathTable1.setDistance(1);
        list.add(treePathTable1);

        TreePathTable treePathTable2 = new TreePathTable();
        treePathTable2.setAncestor("1");
        treePathTable2.setDescendant("1");
        treePathTable2.setDistance(0);
        list.add(treePathTable2);

        TreePathTable treePathTable3 = new TreePathTable();
        treePathTable3.setAncestor("0");
        treePathTable3.setDescendant("2");
        treePathTable3.setDistance(2);
        list.add(treePathTable3);

        TreePathTable treePathTable4 = new TreePathTable();
        treePathTable4.setAncestor("1");
        treePathTable4.setDescendant("2");
        treePathTable4.setDistance(1);
        list.add(treePathTable4);

        TreePathTable treePathTable5 = new TreePathTable();
        treePathTable5.setAncestor("2");
        treePathTable5.setDescendant("2");
        treePathTable5.setDistance(0);
        list.add(treePathTable5);
        treePathTableMapper.insertMore(list);
    }

    @Test
    void deleteByDescendant() {
        List<String> list = Arrays.asList("1", "2");
        treePathTableMapper.deleteByDescendants(list);
    }

    @Test
    void findChildNodeIds() {
        List<String> childNodeIds = treePathTableMapper.findChildNodeIds("0");
        assertNotEquals(0, childNodeIds.size());
    }

    @Test
    void findChildTreePath() {
        List<TreePathTable> childTreePath = treePathTableMapper.findChildTreePath("0");
        assertNotEquals(0, childTreePath.size());
    }

    @Test
    void findChildNodeIdByDistance() {
        List<String> childNodeIdList = treePathTableMapper.findChildNodeIdByDistance("0", 1);
        assertNotEquals(0, childNodeIdList.size());
    }

    @Test
    void findClosestChildNodeIds() {
        List<String> closestChildNodeIds = treePathTableMapper.findClosestChildNodeIds("0");
        assertEquals(1, closestChildNodeIds.size());
    }

    @Test
    void findAllParentNodeIds() {
        List<String> parentNodeIds = treePathTableMapper.findAllParentNodeIds("2");
        assertNotEquals(0, parentNodeIds.size());
    }

    @Test
    void findClosestParentNodeId() {
        String closestParentNodeId = treePathTableMapper.findClosestParentNodeId("2");
        assertEquals("1", closestParentNodeId);
    }

    @Test
    void findAllParentTreePath() {
        List<TreePathTable> allParentTreePath = treePathTableMapper.findAllParentTreePath("2");
        assertNotEquals(0, allParentTreePath.size());
    }
}