package com.tomshidi.mybatis.mapper;

import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.model.PersonInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tomshidi
 * @since 2023/3/27 14:19
 */
@Mapper
public interface PersonInfoMapper {
    void addPersonInfo(@Param("personInfo") @Encrypt PersonInfoEntity personInfoEntity);

    void deletePersonById(@Param("id") String id);

    PersonInfoEntity queryPersonById(@Param("id") String id);

    List<PersonInfoEntity> queryPersonByCondition(@Param("personInfo") @Encrypt PersonInfoEntity personInfoEntity);
}
