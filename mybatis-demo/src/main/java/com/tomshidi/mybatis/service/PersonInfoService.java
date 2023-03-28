package com.tomshidi.mybatis.service;


import com.tomshidi.mybatis.model.PersonInfoEntity;

import java.util.List;

/**
 * @author tomshidi
 * @since 2023/3/24 17:56
 */
public interface PersonInfoService {

    void addPersonInfo(PersonInfoEntity personInfoEntity);

    void savePersonInfo(PersonInfoEntity personInfoEntity);

    void deletePersonById(String id);

    PersonInfoEntity queryPersonById(String id);

    List<PersonInfoEntity> queryPersonByCondition(PersonInfoEntity personInfoEntity);

    List<PersonInfoEntity> queryPersonByName(List<String> nameList);

    List<PersonInfoEntity> queryByCondition(PersonInfoEntity personInfoEntity);
}
