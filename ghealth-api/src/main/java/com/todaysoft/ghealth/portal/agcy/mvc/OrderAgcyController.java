package com.todaysoft.ghealth.portal.agcy.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.agcy.request.MaintainOrderRequest;
import com.todaysoft.ghealth.agcy.request.QueryOrdersRequest;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.base.response.model.OrderHistory;
import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.mgmt.model.ReportGenerateTaskDTO;
import com.todaysoft.ghealth.mgmt.request.DownloadOrderReportRequest;
import com.todaysoft.ghealth.mgmt.request.MaintainOrderUploadRequest;
import com.todaysoft.ghealth.portal.agcy.facade.OrderAgcyFacade;
import com.todaysoft.ghealth.portal.mgmt.facade.OrderMgmtFacade;
import com.todaysoft.ghealth.service.orderEvent.OrderEventLog;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/agcy/order")
public class OrderAgcyController
{
    @Autowired
    private OrderAgcyFacade facade;
    
    @Autowired
    private OrderMgmtFacade orderMgmtFacade;
    
    @RequestMapping("/pager")
    public PagerResponse<Order> pager(@RequestBody QueryOrdersRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/create")
    @OrderEventLog(eventType = "1", handler = "agcy")
    public String create(@RequestBody MaintainOrderRequest request)
    {
        return facade.create(request);
    }
    
    @RequestMapping("/getById")
    public ObjectResponse<Order> geById(@RequestBody MaintainOrderRequest request)
    {
        return facade.getById(request);
    }
    
    @RequestMapping("/cancel")
    @OrderEventLog(eventType = "7", handler = "agcy")
    public String cancel(@RequestBody MaintainOrderRequest request)
    {
        return facade.cancel(request);
    }
    
    @RequestMapping("/place")
    @OrderEventLog(eventType = "2", handler = "agcy")
    public String place(@RequestBody MaintainOrderRequest request)
    {
        return facade.place(request);
    }
    
    @RequestMapping("list")
    public ListResponse<Order> list(@RequestBody QueryOrdersRequest request)
    {
        return facade.list(request);
    }
    
    @RequestMapping("/createOrders")
    public void createOrders(@RequestBody MaintainOrderUploadRequest request)
    {
        facade.createOrders(request);
    }
    
    @RequestMapping("/isUniqueCode")
    public ObjectResponse<Boolean> isUniqueCode(@RequestBody QueryOrdersRequest request)
    {
        return facade.isUniqueCode(request);
    }
    
    @RequestMapping("/getOrderHistoriesByOrderId")
    public ListResponse<OrderHistory> getOrderHistoriesByOrderId(@RequestBody com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest request)
    {
        return facade.getOrderHistoriesByOrderId(request);
    }
    
    @RequestMapping("/report/stream")
    public ObjectResponse<String> getReportStream(@RequestBody DownloadOrderReportRequest request)
    {

        return orderMgmtFacade.getReportUrl(request);
    }

    @RequestMapping("/report/streamForCode")
    public ObjectResponse<OrderReportStreamDTO> getReportStreamForCode(@RequestBody DownloadOrderReportRequest request)
    {

        return orderMgmtFacade.getReportStream(request);
    }
    
    @RequestMapping("/report/generate/details")
    public ObjectResponse<ReportGenerateTaskDTO> getReportGenerateTask(@RequestBody QueryDetailsRequest request)
    {
        return orderMgmtFacade.getReportGenerateTask(request);
    }
    
    @RequestMapping("/createOrderAtMobile")
    public void createOrderAtMobile(@RequestBody MaintainOrderRequest request)
    {
        facade.createOrderAtMobile(request);
    }
    
    @OrderEventLog(eventType = "12", handler = "agcy")
    @RequestMapping("/modify")
    public String modify(@RequestBody MaintainOrderRequest request)
    {
        return facade.modify(request);
    }
    
    @RequestMapping("/getOrderReportDatas")
    public ObjectResponse<byte[]> getOrderReportDatas(@RequestBody MaintainOrderRequest request)
    {
        return facade.getOrderReportDatas(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainOrderRequest request)
    {
        facade.delete(request);
    }
}
