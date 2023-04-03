package com.tomshidi.mybatis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomshidi.mybatis.model.PersonInfoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomshidi
 * @since 2023/3/27 14:43
 */
@SpringBootTest
class PersonInfoMapperTest{

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Test
    void addPersonInfo() {
        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
        personInfoEntity.setId(UUID.randomUUID().toString());
        personInfoEntity.setName("Tohka");
        personInfoEntity.setSex("女");
        personInfoMapper.addPersonInfo(personInfoEntity);
    }

    @Test
    void deletePersonById() {
    }

    @Test
    void queryPersonById() {
    }

    @Test
    void queryPersonByCondition() {
        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
        personInfoEntity.setName("Tohka");
        personInfoEntity.setSex("女");
        personInfoEntity.setDesc("你好");
        List<PersonInfoEntity> personInfoEntityList = personInfoMapper.queryPersonListByCondition(personInfoEntity);
        assertNotEquals(0, personInfoEntityList.size());
    }

    @Test
    void queryPersonPageByCondition() {
        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
        personInfoEntity.setName("Tohka");
        personInfoEntity.setSex("女");
        personInfoEntity.setDesc("你好");
        Page<PersonInfoEntity> page = Page.of(0, 10);
        Page<PersonInfoEntity> personInfoEntityPage = personInfoMapper.queryPersonPageByCondition(page, personInfoEntity);
        assertNotEquals(0, personInfoEntityPage.getTotal());
    }
}