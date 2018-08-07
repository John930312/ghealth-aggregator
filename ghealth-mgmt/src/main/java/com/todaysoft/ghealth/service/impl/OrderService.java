package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.OrderSimpleDTO;
import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.mgmt.model.ReportGenerateTaskDTO;
import com.todaysoft.ghealth.mgmt.request.*;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderHistory;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.model.order.ReportGenerateTask;
import com.todaysoft.ghealth.model.statistics.StatisticsSearcher;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.IShortMessageService;
import com.todaysoft.ghealth.service.wrapper.OrderWrapper;
import com.todaysoft.ghealth.service.wrapper.ReportGenerateTaskWrapper;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.support.ServiceException;
import com.todaysoft.ghealth.utils.DownloadFilesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class OrderService implements IOrderService
{
    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private OrderWrapper orderWrapper;
    
    @Autowired
    private ReportGenerateTaskWrapper reportGenerateTaskWrapper;
    
    @Autowired
    private IShortMessageService shortMessageService;
    
    @Override
    public Pagination<Order> search(OrderSearcher searcher, int pageNo, int pageSize)
    {
        QueryOrdersRequest request = orderWrapper.searcherWarp(searcher);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Order> response =
            gateway.request("/mgmt/order/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Order> pager = response.getData();
        
        Pagination<Order> pagination = new Pagination<Order>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        pagination.setRecords(orderWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public Map<String, Order> getOrdersAsCodeMappings(List<String> codes)
    {
        if (CollectionUtils.isEmpty(codes))
        {
            return Collections.emptyMap();
        }
        
        Set<String> args = new HashSet<String>();
        
        for (String code : codes)
        {
            if (!StringUtils.isEmpty(code))
            {
                args.add(code);
            }
        }
        
        if (CollectionUtils.isEmpty(args))
        {
            return Collections.emptyMap();
        }
        
        QueryOrderByCodesRequest request = new QueryOrderByCodesRequest();
        request.setCodes(args);
        
        ListResponse<OrderSimpleDTO> response =
            gateway.request("/mgmt/order/list/codes", request, new ParameterizedTypeReference<ListResponse<OrderSimpleDTO>>()
            {
            });
        
        List<OrderSimpleDTO> records = response.getData();
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyMap();
        }
        
        Order order;
        Map<String, Order> mappings = new HashMap<String, Order>();
        
        for (OrderSimpleDTO record : records)
        {
            order = new Order();
            order.setId(record.getId());
            order.setCode(record.getCode());
            order.setStatus(record.getStatus());
            order.setLocusGenetypeDTOS(record.getLocusGenetypeDTOS());
            mappings.put(order.getCode(), order);
        }
        
        return mappings;
    }
    
    @Override
    public Order get(String id)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Order> result =
            gateway.request("/mgmt/order/display", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        if (null == result.getData())
        {
            return new Order();
        }
        return orderWrapper.wrap(result.getData());
    }
    
    @Override
    public List<Order> list(OrderSearcher searcher)
    {
        QueryOrdersRequest request = orderWrapper.searcherWarp(searcher);
        ListResponse<com.todaysoft.ghealth.base.response.model.Order> result =
            gateway.request("/mgmt/order/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        List<Order> orders = new ArrayList<Order>();
        if (null == result.getData())
        {
            return orders;
        }
        return orderWrapper.wrap(result.getData());
    }
    
    @Override
    public List<Order> getOrdersByIds(String orderIds)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(orderIds);
        ListResponse<com.todaysoft.ghealth.base.response.model.Order> response = gateway
            .request("/mgmt/order/getOrdersByIds", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        
        return orderWrapper.wrap(response.getData());
    }
    
    @Override
    public List<OrderHistory> getOrderHistoriesByOrderId(String orderId)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(orderId);
        ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory> response = gateway.request("/mgmt/order/getOrderHistoriesByOrderId",
            request,
            new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory>>()
            {
            });
        if (null == response.getData())
        {
            return Collections.emptyList();
        }
        return orderWrapper.wrapOrderHistory(response.getData());
    }
    
    @Override
    public String generateReports(String ids)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(ids);
        ListResponse<String> response = gateway.request("/mgmt/order/report/generates", request, new ParameterizedTypeReference<ListResponse<String>>()
        {
        });
        List<String> list = response.getData();
        String taskIds = "";
        if (!CollectionUtils.isEmpty(list))
        {
            for (String taskId : list)
            {
                taskIds += taskId + "-";
            }
        }
        return taskIds;
    }
    
    @Override
    public List<Order> setOtherVal(List<Order> list)
    {
        if (!CollectionUtils.isEmpty(list))
        {
            list.forEach(order -> {
                if (null != order.getAgency())
                {
                    order.setAgencyName(order.getAgency().getName());
                }
                if (null != order.getProduct())
                {
                    order.setProductName(order.getProduct().getName());
                }
                if (null != order.getCustomer())
                {
                    order.setCustomerName(order.getCustomer().getName());
                }
            });
        }
        return list;
    }
    
    @Override
    public void download(String url, HttpServletResponse response)
    {
        DownloadFilesUtil.download(response, url);
    }
    
    @Override
    public String generateReport(String id)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(id);
        ObjectResponse<String> response = gateway.request("/mgmt/order/report/generate", request, new ParameterizedTypeReference<ObjectResponse<String>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public String regenerateReport(String id)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(id);
        ObjectResponse<String> response = gateway.request("/mgmt/order/report/regenerate", request, new ParameterizedTypeReference<ObjectResponse<String>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public ReportGenerateTask getReportGenerateTask(String reportGenerateTaskId)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(reportGenerateTaskId);
        
        ObjectResponse<ReportGenerateTaskDTO> response =
            gateway.request("/mgmt/order/report/generate/details", request, new ParameterizedTypeReference<ObjectResponse<ReportGenerateTaskDTO>>()
            {
            });
        return reportGenerateTaskWrapper.wrap(response.getData());
    }
    
    @Override
    public Map<String, Object> getReportGenerateTasks(String reportGenerateTaskId)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(reportGenerateTaskId);
        
        ListResponse<ReportGenerateTaskDTO> response =
            gateway.request("/mgmt/order/report/generate/orderDetails", request, new ParameterizedTypeReference<ListResponse<ReportGenerateTaskDTO>>()
            {
            });
        List<ReportGenerateTask> tasks = reportGenerateTaskWrapper.wrap(response.getData());
        Map<String, Object> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(tasks))
        {
            for (ReportGenerateTask task : tasks)
            {
                if ("0".equals(task.getStatus()))
                {
                    map.put("task", task);
                    break;
                }
                
                if ("2".equals(task.getStatus()))
                {
                    map.put("task", task);
                    break;
                }
                map.put("task", task);
            }
        }
        return map;
    }
    
    @Override
    public void modify(Order data)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        BeanUtils.copyProperties(data, request, "customer", "product", "agency");
        request.setProductId(data.getProduct().getId());
        request.setCustomerId(data.getCustomer().getId());
        request.setAgencyId(data.getAgency().getId());
        gateway.request("/mgmt/order/modify", request);
    }
    
    @Override
    public boolean isUniqueCode(String id, String code)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setCode(code);
        request.setId(id);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/order/isUniqueCode", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public OrderReportStreamDTO getReport(String id, String type)
    {
        DownloadOrderReportRequest request = new DownloadOrderReportRequest();
        request.setOrderId(id);
        request.setType(type);
        request.setLoginType("1");
        
        ObjectResponse<OrderReportStreamDTO> response =
            gateway.request("/mgmt/order/report/stream", request, new ParameterizedTypeReference<ObjectResponse<OrderReportStreamDTO>>()
            {
            });
        
        return response.getData();
    }
    
    @Override
    public List<OrderHistory> getOrderHistories(String name, String year, String month)
    {
        MaintainOrderHistoryRequest request = new MaintainOrderHistoryRequest();
        request.setName(name);
        request.setYear(year);
        request.setMonth(month);
        ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory> response = gateway.request("/mgmt/order/getOrderHistories",
            request,
            new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory>>()
            {
            });
        if (null == response.getData())
        {
            return Collections.emptyList();
        }
        return orderWrapper.wrapOrderHistory(response.getData());
    }
    
    @Override
    public List<OrderHistory> getOrderHistoryLists(String name, String year, String month, String day)
    {
        MaintainOrderHistoryRequest request = new MaintainOrderHistoryRequest();
        request.setName(name);
        request.setYear(year);
        request.setMonth(month);
        request.setDay(day);
        ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory> response = gateway.request("/mgmt/order/getOrderHistoryLists",
            request,
            new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory>>()
            {
            });
        if (null == response.getData())
        {
            return Collections.emptyList();
        }
        return orderWrapper.wrapOrderHistory(response.getData());
    }
    
    @Override
    public List<OrderHistory> getOrderHistory(StatisticsSearcher searcher)
    {
        QueryOrderHistoryRequest request = orderWrapper.searcherOrderHistoryWarp(searcher);
        ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory> response = gateway.request("/mgmt/order/getOrderHistory",
            request,
            new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory>>()
            {
            });
        if (null == response.getData())
        {
            return Collections.emptyList();
        }
        return orderWrapper.wrapOrderHistory(response.getData());
    }
    
    @Override
    public String path(String id)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(id);
        ObjectResponse<String> response = gateway.request("/mgmt/order/getPath", request, new ParameterizedTypeReference<ObjectResponse<String>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public Order getByCode(String code)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setCode(code);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Order> result =
            gateway.request("/mgmt/order/getByCode", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        if (null == result.getData())
        {
            return new Order();
        }
        return orderWrapper.wrap(result.getData());
    }
    
    @Override
    public void cancel(Order data)
    {
        for (String id : data.getId().split("-"))
        {
            MaintainOrderRequest request = new MaintainOrderRequest();
            request.setId(id);
            gateway.request("/mgmt/order/cancel", request);
        }
    }
    
    @Override
    public String dataDetails(String orderId)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to query testing data details for order {}.", orderId);
        }
        
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(orderId);
        ObjectResponse<String> response = gateway.request("/mgmt/order/dataDetails", request, new ParameterizedTypeReference<ObjectResponse<String>>()
        {
        });
        String data = response.getData();
        
        if (!StringUtils.isEmpty(data))
        {
            data = data.replaceAll("geneType", "genetype");
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Order {} testing data details {}", orderId, data);
        }
        
        return data;
    }
    
    @Override
    public void sendMessageToAgency(Order order)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setCustomerId(order.getCustomer().getId());
        request.setAgencyId(order.getAgency().getId());
        gateway.request("/mgmt/smsSend/reportGeneratedSendToAgcy", request);
    }
    
    @Override
    public void uploadReport(Order order, MultipartFile data) throws IOException
    {
        Optional.ofNullable(data).orElseThrow(() -> new ServiceException("上传文件为空"));
        MaintainOrderRequest request = new MaintainOrderRequest();
        BeanUtils.copyProperties(order, request);
        request.setFileName(data.getOriginalFilename());
        request.setBytes(data.getBytes());
        gateway.request("/mgmt/order/upload", request);
    }
    
    @Override
    public void delete(String id)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(id);
        gateway.request("/mgmt/order/delete", request);
    }

    @Override
    public Pagination<Order> searchSpecialList(OrderSearcher searcher, int pageNo, int pageSize)
    {
        QueryOrdersRequest request = orderWrapper.searcherWarp(searcher);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Order> response =
                gateway.request("/mgmt/order/specialPager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Order>>()
                {
                });
        Pager<com.todaysoft.ghealth.base.response.model.Order> pager = response.getData();

        Pagination<Order> pagination = new Pagination<Order>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());

        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        pagination.setRecords(orderWrapper.wrap(pager.getRecords()));
        return pagination;
    }
}
