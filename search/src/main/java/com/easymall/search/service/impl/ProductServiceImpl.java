package com.easymall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.easymall.search.config.EasymallElasticSearchConfig;
import com.easymall.search.service.ProductService;
import easymall.easymallcommon.to.es.SkuEsModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Name: ProductServiceImpl
 * @Author peipei
 * @Date 2022/4/7
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public boolean saveProductAsIndices(List<SkuEsModel> skuEsModelList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModelList) {
            IndexRequest indexRequest = new IndexRequest("product");
            String s = JSON.toJSONString(skuEsModel);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, EasymallElasticSearchConfig.COMMON_OPTIONS);
        boolean hasFailures = bulkResponse.hasFailures();
        List<BulkItemResponse> itemResponses = Arrays.asList(bulkResponse.getItems());
        List<String> collect = itemResponses.stream().map(BulkItemResponse::getId).collect(Collectors.toList());
        log.info("商品上架完成 {} ", collect);
        return !hasFailures;

    }
}
