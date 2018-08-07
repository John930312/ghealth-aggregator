package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.mgmt.request.*;
import com.todaysoft.ghealth.service.orderEvent.OrderEventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.OrderSimpleDTO;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.base.response.model.OrderHistory;
import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.mgmt.model.ReportGenerateTaskDTO;
import com.todaysoft.ghealth.portal.mgmt.facade.OrderMgmtFacade;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mgmt/order")
public class OrderMgmtController
{
    @Autowired
    private OrderMgmtFacade facade;
    
    @RequestMapping("pager")
    public PagerResponse<Order> pager(@RequestBody QueryOrdersRequest request)
    {
        return facade.pager(request);
    }

    @RequestMapping("specialPager")
    public PagerResponse<Order> specialPager(@RequestBody QueryOrdersRequest request)
    {
        return facade.specialPager(request);
    }
    
    @RequestMapping("/display")
    public ObjectResponse<Order> diaplsy(@RequestBody MaintainOrderRequest request)
    {
        return facade.display(request);
    }
    
    @RequestMapping("list")
    public ListResponse<Order> list(@RequestBody QueryOrdersRequest request)
    {
        return facade.list(request);
    }
    
    @RequestMapping("/list/codes")
    public ListResponse<OrderSimpleDTO> list(@RequestBody QueryOrderByCodesRequest request)
    {
        return facade.list(request);
    }
    
    @RequestMapping("/getOrdersByIds")
    public ListResponse<Order> getOrdersByIds(@RequestBody MaintainOrderRequest request)
    {
        return facade.getOrdersByIds(request);
    }
    
    @RequestMapping("/report/generate")
    public ObjectResponse<String> generateReport(@RequestBody MaintainOrderRequest request)
    {
        return facade.generateReport(request);
    }
    
    @RequestMapping("/report/generates")
    public ListResponse<String> generateReports(@RequestBody MaintainOrderRequest request)
    {
        return facade.generateReports(request);
    }
    
    @RequestMapping("/report/regenerate")
    public ObjectResponse<String> regenerateReport(@RequestBody MaintainOrderRequest request)
    {
        return facade.regenerateReport(request);
    }
    
    @RequestMapping("/report/generate/details")
    public ObjectResponse<ReportGenerateTaskDTO> getReportGenerateTask(@RequestBody QueryDetailsRequest request)
    {
        return facade.getReportGenerateTask(request);
    }
    
    @RequestMapping("/report/generate/orderDetails")
    public ListResponse<ReportGenerateTaskDTO> getReportGenerateTasks(@RequestBody QueryDetailsRequest request)
    {
        return facade.getReportGenerateTasks(request);
    }
    
    @RequestMapping("/report/stream")
    public ObjectResponse<OrderReportStreamDTO> getReportStream(@RequestBody DownloadOrderReportRequest request)
    {
        return facade.getReportStream(request);
    }
    
    @RequestMapping("/getOrderHistoriesByOrderId")
    public ListResponse<OrderHistory> getOrderHistoriesByOrderId(@RequestBody MaintainOrderRequest request)
    {
        return facade.getOrderHistoriesByOrderId(request);
    }
    
    @RequestMapping("/modify")
    @OrderEventLog(eventType = "12", handler = "mgmt")
    public String modify(@RequestBody MaintainOrderRequest request)
    {
        return facade.modify(request);
    }
    
    @RequestMapping("/isUniqueCode")
    public ObjectResponse<Boolean> isUniqueCode(@RequestBody MaintainOrderRequest request)
    {
        return facade.isUniqueCode(request);
    }
    
    @RequestMapping("/getOrderHistories")
    public ListResponse<OrderHistory> getOrderHistories(@RequestBody MaintainOrderHistoryRequest request)
    {
        return facade.getOrderHistories(request);
    }
    
    @RequestMapping("/getOrderHistoryLists")
    public ListResponse<OrderHistory> getOrderHistoryLists(@RequestBody MaintainOrderHistoryRequest request)
    {
        return facade.getOrderHistoryLists(request);
    }
    
    @RequestMapping("/getOrderHistory")
    public ListResponse<OrderHistory> getOrderHistory(@RequestBody QueryOrderHistoryRequest request)
    {
        return facade.getOrderHistory(request);
    }
    
    @RequestMapping("/getPath")
    public ObjectResponse<String> getPath(@RequestBody MaintainOrderRequest request)
    {
        return facade.getPath(request);
    }
    
    @RequestMapping("/getByCode")
    public ObjectResponse<Order> getByCode(@RequestBody MaintainOrderRequest request)
    {
        return facade.getByCode(request);
    }
    
    @RequestMapping("/cancel")
    @OrderEventLog(eventType = "7", handler = "mgmt")
    public String cancel(@RequestBody MaintainOrderRequest request)
    {
        return facade.cancel(request);
    }
    
    @RequestMapping("/dataDetails")
    public ObjectResponse<String> dataDetails(@RequestBody MaintainOrderRequest request)
    {
        return facade.dataDetails(request);
    }
    
    @RequestMapping("/upload")
    public void upload(@RequestBody MaintainOrderRequest request) throws IOException
    {
        facade.upload(request);
    }
    
    @RequestMapping("/getStatisticsExcels")
    public ListResponse<List<String>> getStatisticsExcels(@RequestBody QueryOrderHistoryRequest request)
    {
        return facade.getStatisticsExcels(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainOrderRequest request)
    {
        facade.delete(request);
    }

    @RequestMapping("/uploadReport")
    public void uploadReport(@RequestBody MaintainOrderRequest request) throws IOException
    {
        facade.uploadReport(request);
    }
}
