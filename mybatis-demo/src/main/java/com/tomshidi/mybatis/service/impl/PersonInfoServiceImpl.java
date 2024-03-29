package com.tomshidi.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.mybatis.model.PersonInfoEntity;
import com.tomshidi.mybatis.mapper.PersonInfoMapper;
import com.tomshidi.mybatis.mapper.PersonInfoPlusMapper;
import com.tomshidi.mybatis.service.PersonInfoService;
import com.tomshidi.mybatis.wrappers.EncryptLambdaQueryWrapper;
import com.tomshidi.mybatis.wrappers.EncryptQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author tomshidi
 * @since 2023/3/24 18:00
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PersonInfoServiceImpl extends ServiceImpl<PersonInfoPlusMapper, PersonInfoEntity> implements PersonInfoService {

    private PersonInfoPlusMapper personInfoPlusMapper;

    private PersonInfoMapper personInfoMapper;

    @Autowired
    public PersonInfoServiceImpl(PersonInfoPlusMapper personInfoPlusMapper, PersonInfoMapper personInfoMapper) {
        this.personInfoPlusMapper = personInfoPlusMapper;
        this.personInfoMapper = personInfoMapper;
    }

    @Override
    public void addPersonInfo(PersonInfoEntity personInfoEntity) {
        personInfoPlusMapper.insert(personInfoEntity);
    }

    @Override
    public void savePersonInfo(PersonInfoEntity personInfoEntity) {
        personInfoMapper.addPersonInfo(personInfoEntity);
    }

    @Override
    public void savePersonInfos(List<PersonInfoEntity> personInfoEntityList) {
        saveBatch(personInfoEntityList);
    }

    @Override
    public void deletePersonById(String id) {
        personInfoPlusMapper.deleteById(id);
    }

    @Override
    public PersonInfoEntity queryPersonById(String id) {
        return personInfoPlusMapper.selectById(id);
    }

    @Override
    @Encrypt
    public List<PersonInfoEntity> queryPersonByCondition(PersonInfoEntity personInfoEntity) {
        LambdaQueryWrapper<PersonInfoEntity> queryWrapper = new EncryptLambdaQueryWrapper<>();
        queryWrapper.eq(!ObjectUtils.isEmpty(personInfoEntity.getName()), PersonInfoEntity::getName, personInfoEntity.getName())
                .eq(!ObjectUtils.isEmpty(personInfoEntity.getSex()), PersonInfoEntity::getSex, personInfoEntity.getSex())
                .like(!ObjectUtils.isEmpty(personInfoEntity.getDesc()), PersonInfoEntity::getDesc, personInfoEntity.getDesc());
        return personInfoPlusMapper.selectList(queryWrapper);
    }

    @Override
    public List<PersonInfoEntity> queryPersonByName(List<String> nameList) {
        LambdaQueryWrapper<PersonInfoEntity> lambdaQueryWrapper = new EncryptLambdaQueryWrapper<>();
        lambdaQueryWrapper.in(!ObjectUtils.isEmpty(nameList), PersonInfoEntity::getName, nameList);
        return personInfoPlusMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<PersonInfoEntity> queryByCondition(PersonInfoEntity personInfoEntity) {
        QueryWrapper<PersonInfoEntity> queryWrapper = new EncryptQueryWrapper<>();
        queryWrapper.setEntityClass(PersonInfoEntity.class)
                .eq(!ObjectUtils.isEmpty(personInfoEntity.getName()), "name", personInfoEntity.getName())
                .eq(!ObjectUtils.isEmpty(personInfoEntity.getSex()), "sex", personInfoEntity.getSex())
                .like(!ObjectUtils.isEmpty(personInfoEntity.getDesc()), "desc", personInfoEntity.getDesc());
        return personInfoPlusMapper.selectList(queryWrapper);
    }
}
