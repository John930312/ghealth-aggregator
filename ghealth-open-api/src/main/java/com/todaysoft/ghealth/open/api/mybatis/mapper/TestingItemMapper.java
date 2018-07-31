package com.todaysoft.ghealth.open.api.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.open.api.mybatis.model.TestingItem;
import com.todaysoft.ghealth.open.api.mybatis.model.TestingItemQuery;

public interface TestingItemMapper
{
    List<TestingItem> query(TestingItemQuery query);
}
