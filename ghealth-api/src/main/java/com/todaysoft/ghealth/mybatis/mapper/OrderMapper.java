package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.ObjectStorage;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface OrderMapper
{
    List<Order> search(OrderSearcher searcher);
    
    int count(OrderSearcher searcher);
    
    List<Order> getOrdersByCodes(@Param("codes") Set<String> codes);
    
    int deleteByPrimaryKey(String id);
    
    int insert(Order record);
    
    int insertSelective(Order record);
    
    Order get(String id);

    Order getOrderDataByTaskId(String id);

    int modify(Order record);
    
    int modifyState(Order record);
    
    List<String> getCodes();

    Order getOrderByTaskId(String taskId);

    Order getByCode(String code);

    String getDataDetails(String orderId);

    List<String> orderIdList(OrderSearcher searcher);

    List<Order> getOrdersByCustomerPhone(String phone);

    ObjectStorage getPdfReportUrl(String orderId);

    ObjectStorage getDocReportUrl(String orderId);

    List<String> getLoci(String id);

    List<String> getOrderCodes();

    List<Order> getOrderDetailsByCodes(@Param("codes") Set<String> codes);
}