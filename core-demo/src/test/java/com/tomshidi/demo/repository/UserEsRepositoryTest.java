package com.tomshidi.demo.repository;

import com.tomshidi.demo.entity.UserEntity;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tomshidi
 * @date 2022/11/23 16:05
 */
@SpringBootTest
class UserEsRepositoryTest {

    @Autowired
    private UserEsRepository userEsRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testSave() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("tomshidi");
        userEntity.setAge(3);
        userEntity.setAddress("非洲");
        UserEntity result = userEsRepository.save(userEntity);
        assertNotNull(result);
    }

    @Test
    public void testSearch() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("user_name", "tomshidi"));
        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(0, 200))
                .build();
        SearchHits<UserEntity> searchHits = elasticsearchRestTemplate.search(query, UserEntity.class);
        searchHits.getSearchHits();
    }
}