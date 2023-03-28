package com.tomshidi.mybatis.service.impl;

import com.tomshidi.mybatis.model.PersonInfoEntity;
import com.tomshidi.mybatis.service.PersonInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomshidi
 * @since 2023/3/24 18:38
 */
@SpringBootTest
class PersonInfoServiceImplTest {

    @Autowired
    private PersonInfoService personInfoService;

    @Test
    void addPersonInfo() {
        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
        personInfoEntity.setId(UUID.randomUUID().toString());
        personInfoEntity.setName("Tohka");
        personInfoEntity.setSex("女");
        personInfoEntity.setHireDate(LocalDateTime.now());
        personInfoService.addPersonInfo(personInfoEntity);
    }

    @Test
    void savePersonInfo() {
        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
        personInfoEntity.setId(UUID.randomUUID().toString());
        personInfoEntity.setName("Yoxino");
        personInfoEntity.setSex("女");
        personInfoEntity.setHireDate(LocalDateTime.now());
        personInfoService.savePersonInfo(personInfoEntity);
    }

    @Test
    void deletePersonById() {
        personInfoService.deletePersonById("sssss");
    }

    @Test
    void queryPersonById() {
        PersonInfoEntity personInfoEntity = personInfoService.queryPersonById("abf328bd-a7f5-4686-a92d-ea7f02a8bdaf");
        assertNotNull(personInfoEntity);
    }

    @Test
    void queryPersonByCondition() {
        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
        personInfoEntity.setName("Tohka");
        personInfoEntity.setSex("女");
        List<PersonInfoEntity> personInfoEntityList = personInfoService.queryPersonByCondition(personInfoEntity);
        assertNotEquals(0, personInfoEntityList.size());
    }

    @Test
    void queryPersonByName() {
        List<String> nameList = Stream.of("Tohka", "tomshidi").collect(Collectors.toList());
        List<PersonInfoEntity> personInfoEntities = personInfoService.queryPersonByName(nameList);
        assertNotEquals(0, personInfoEntities.size());
    }

    @Test
    void queryByCondition() {
        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
        personInfoEntity.setName("Tohka");
        personInfoEntity.setSex("女");
        List<PersonInfoEntity> personInfoEntityList = personInfoService.queryByCondition(personInfoEntity);
        assertNotEquals(0, personInfoEntityList.size());
    }
}