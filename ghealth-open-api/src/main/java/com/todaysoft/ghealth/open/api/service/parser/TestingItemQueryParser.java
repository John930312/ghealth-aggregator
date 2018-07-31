package com.todaysoft.ghealth.open.api.service.parser;

import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.open.api.mybatis.model.TestingItemQuery;
import com.todaysoft.ghealth.open.api.restful.request.TestingItemQueryRequest;

@Component
public class TestingItemQueryParser extends QueryParser<TestingItemQueryRequest, TestingItemQuery>
{

}
