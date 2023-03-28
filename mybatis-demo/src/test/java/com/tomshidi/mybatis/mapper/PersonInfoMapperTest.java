package com.tomshidi.mybatis.mapper;

import com.tomshidi.model.PersonInfoEntity;
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
        personInfoEntity.setName("tomshidi");
        personInfoEntity.setSex("男");
        List<PersonInfoEntity> personInfoEntityList = personInfoMapper.queryPersonByCondition(personInfoEntity);
        assertNotEquals(0, personInfoEntityList.size());
    }
}