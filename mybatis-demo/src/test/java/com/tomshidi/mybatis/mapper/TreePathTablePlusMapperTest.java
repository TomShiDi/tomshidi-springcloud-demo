package com.tomshidi.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tomshidi.model.TreePathTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tangshili
 * @date 2023/2/22 15:52
 */
@SpringBootTest
class TreePathTablePlusMapperTest {
    @Autowired
    private TreePathTablePlusMapper treePathTablePlusMapper;

    @Test
    public void testQueryById() {
        QueryWrapper<TreePathTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ancestor", "0");
        List<TreePathTable> treePathTableList = treePathTablePlusMapper.selectList(queryWrapper);
        assertNotNull(treePathTableList);
    }
}