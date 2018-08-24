package com.todaysoft.ghealth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderHistory;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.model.order.ReportGenerateTask;
import com.todaysoft.ghealth.model.statistics.StatisticsSearcher;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.web.multipart.MultipartFile;

public interface IOrderService
{
    Pagination<Order> search(OrderSearcher searcher, int pageNo, int pageSize);
    
    Map<String, Order> getOrdersAsCodeMappings(List<String> codes);
    
    Order get(String id);
    
    List<Order> list(OrderSearcher searcher);
    
    List<Order> setOtherVal(List<Order> list);
    
    void download(String url, HttpServletResponse response);
    
    List<Order> getOrdersByIds(String orderIds);
    
    String generateReport(String id);
    
    String regenerateReport(String id);
    
    ReportGenerateTask getReportGenerateTask(String reportGenerateTaskId);
    
    OrderReportStreamDTO getReport(String id, String type);

    List<OrderHistory> getOrderHistoriesByOrderId(String orderId);
    
    String generateReports(String ids);
    
    Map<String, Object> getReportGenerateTasks(String reportGenerateTaskId);
    
    void modify(Order data);
    
    boolean isUniqueCode(String id, String code);
    
    List<OrderHistory> getOrderHistories(String name, String year, String month);
    
    List<OrderHistory> getOrderHistoryLists(String name, String year, String month, String day);
    
    List<OrderHistory> getOrderHistory(StatisticsSearcher searcher);
    
    String path(String id);

    Order getByCode(String code);

    void cancel(Order order);

    String dataDetails(String orderId);

    void sendMessageToAgency(Order order);

    void uploadReport(Order order, MultipartFile file) throws Exception;

    void delete(String id);

    Pagination<Order> searchSpecialList(OrderSearcher searcher, int offset, int limit);

    String getReportUrl(String id, String type);

    boolean setVigilance(Order request);

    Map<String,List<Order>> getSpecialOrdersAsCodeMappings(List<String> orderCodes);
}
