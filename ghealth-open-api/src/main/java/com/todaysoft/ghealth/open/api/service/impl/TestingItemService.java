package com.todaysoft.ghealth.open.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsgene.restful.response.DataResponse;
import com.hsgene.restful.util.CountRecords;
import com.todaysoft.ghealth.open.api.mybatis.mapper.TestingItemMapper;
import com.todaysoft.ghealth.open.api.mybatis.model.TestingItem;
import com.todaysoft.ghealth.open.api.mybatis.model.TestingItemQuery;
import com.todaysoft.ghealth.open.api.restful.model.TestingItemDTO;
import com.todaysoft.ghealth.open.api.restful.request.TestingItemQueryRequest;
import com.todaysoft.ghealth.open.api.service.ITestingItemService;
import com.todaysoft.ghealth.open.api.service.parser.TestingItemQueryParser;
import com.todaysoft.ghealth.open.api.service.wrapper.TestingItemWrapper;

@Service
public class TestingItemService implements ITestingItemService
{
    @Autowired
    private TestingItemMapper testingItemMapper;

    @Autowired
    private TestingItemWrapper testingItemWrapper;

    @Autowired
    private TestingItemQueryParser testingItemQueryParser;

    @Override
    public DataResponse<CountRecords<TestingItemDTO>> list(TestingItemQueryRequest request)
    {
        TestingItemQuery query = testingItemQueryParser.parse(request);
        CountRecords<TestingItemDTO> data = new CountRecords<>();
        List<TestingItem> records = testingItemMapper.query(query);
        data.setRecords(testingItemWrapper.wrap(records));
        return new DataResponse<>(data);
    }
}
