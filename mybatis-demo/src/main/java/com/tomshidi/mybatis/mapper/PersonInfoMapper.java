package com.tomshidi.mybatis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.mybatis.model.PersonInfoEntity;
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

    List<PersonInfoEntity> queryPersonListByCondition(@Param("personInfo") @Encrypt PersonInfoEntity personInfoEntity);

    Page<PersonInfoEntity> queryPersonPageByCondition(Page<PersonInfoEntity> page, @Param("personInfo") @Encrypt PersonInfoEntity personInfoEntity);
}
