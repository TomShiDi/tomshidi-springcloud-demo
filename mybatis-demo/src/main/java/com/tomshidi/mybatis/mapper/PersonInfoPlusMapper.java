package com.tomshidi.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomshidi.model.PersonInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tomshidi
 * @since 2023/3/24 17:54
 */
@Mapper
public interface PersonInfoPlusMapper extends BaseMapper<PersonInfoEntity> {

}
