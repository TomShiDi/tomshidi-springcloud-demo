package com.tomshidi.esdemo.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.tomshidi.esdemo.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

import java.io.IOException;
import java.util.List;

/**
 * @author tomshidi
 * @date 2022/11/23 16:05
 */
@SpringBootTest
class UserEsRepositoryTest {

    @Autowired
    private UserEsRepository userEsRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Test
    public void testSave() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("tomshidi");
        userEntity.setAge(3);
        userEntity.setAddress("非洲");
        UserEntity result = userEsRepository.save(userEntity);
        Assertions.assertNotNull(result);
    }

//    @Test
//    public void testSearch() {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//                .must(QueryBuilders.termQuery("user_name", "tomshidi"));
//        Query query = new NativeSearchQueryBuilder()
//                .withQuery(boolQueryBuilder)
//                .withPageable(PageRequest.of(0, 200))
//                .build();
//        SearchHits<UserEntity> searchHits = elasticsearchRestTemplate.search(query, UserEntity.class);
//        searchHits.getSearchHits();
//    }

    @Test
    public void testSearch() throws IOException {
        SearchResponse<UserEntity> response = elasticsearchClient.search(s -> s.index("user_data")
                        .query(builder -> builder.bool(bq -> bq.must(q -> q.term(tq -> tq.field("user_name").value("tomshidi"))))),
                UserEntity.class);
        List<UserEntity> userEntities = response.hits().hits().stream().map(Hit::source).toList();
        Assertions.assertNotNull(userEntities);
    }
}