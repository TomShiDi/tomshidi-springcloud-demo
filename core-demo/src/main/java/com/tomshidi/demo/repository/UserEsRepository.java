package com.tomshidi.demo.repository;

import com.tomshidi.demo.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tomshidi
 * @date 2022/11/23 16:04
 */
@Repository
public interface UserEsRepository extends ElasticsearchRepository<UserEntity, String> {

}
