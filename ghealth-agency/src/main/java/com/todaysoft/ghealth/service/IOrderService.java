package com.todaysoft.ghealth.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.ghealth.base.response.model.OrderUploadRequest;
import com.todaysoft.ghealth.model.*;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.support.Pagination;

public interface IOrderService
{
    Pagination<Order> search(OrderSearcher searcher, int pageNo, int pageSize);
    
    OrderDetails getOrder(String id);
    
    void create(Order data);
    
    void modify(Order data);
    
    void cancel(Order order);
    
    void place(Order Data);
    
    Order getOrderById(String id);
    
    List<Order> list(OrderSearcher searcher);
    
    OrderUploadData parse(@RequestParam MultipartFile uploadData);
    
    List<Order> setOtherVal(List<Order> list);
    
    Boolean isUniqueCode(String id, String code);
    
    List<OrderHistory> getOrderHistoriesByOrderId(String orderId);
    
    ReportGenerateTask getReportGenerateTask(String reportGenerateTaskId);

    String getReport(String id, String type);

    OrderReportStreamDTO getReportForCode(String id, String type);

    void createOrderAtMobile(Order data);
    
    void upload(List<OrderUploadRequest> datas);

    void delete(String id);
}
