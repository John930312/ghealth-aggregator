package com.todaysoft.ghealth.open.api.service;

import com.hsgene.restful.response.DataResponse;
import com.hsgene.restful.util.CountRecords;
import com.todaysoft.ghealth.open.api.restful.model.TestingItemDTO;
import com.todaysoft.ghealth.open.api.restful.request.TestingItemQueryRequest;

public interface ITestingItemService
{
    DataResponse<CountRecords<TestingItemDTO>> list(TestingItemQueryRequest request);
}
