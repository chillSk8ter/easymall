package com.easymall.search;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
class SearchApplicationTests {
    static class User {
        Integer age;
        String name;

        public User(String name) {
            this.name = name;
        }
    }

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() throws IOException {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");
        User user = new User("pp");
        String jsonStr = JSON.toJSONString(user);
        IndexRequest source = indexRequest.source(jsonStr, XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(source, RequestOptions.DEFAULT);
        System.out.println(response);
    }


}
