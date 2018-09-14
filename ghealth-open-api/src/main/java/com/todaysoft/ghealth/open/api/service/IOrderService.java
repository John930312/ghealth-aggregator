package com.todaysoft.ghealth.open.api.service;

import com.hsgene.restful.response.DataResponse;
import com.hsgene.restful.util.CountRecords;
import com.todaysoft.ghealth.open.api.restful.model.OrderDTO;
import com.todaysoft.ghealth.open.api.restful.model.TestingItemReportDTO;
import com.todaysoft.ghealth.open.api.restful.request.GhealthDatas;
import com.todaysoft.ghealth.open.api.restful.request.OrderQueryRequest;

import java.util.List;


public interface IOrderService
{
    DataResponse<CountRecords<OrderDTO>> list(OrderQueryRequest request);
    
    DataResponse<OrderDTO> get(String id);

    DataResponse<List<TestingItemReportDTO>> getTestingItemsReport(String id);

    DataResponse<OrderDTO> getEntityByCode(String code);

    DataResponse<String> createDatas(GhealthDatas datas);

    DataResponse<Boolean> validateHaveCode(String code);
}
