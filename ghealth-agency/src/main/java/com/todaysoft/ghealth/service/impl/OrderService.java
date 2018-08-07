package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.MaintainOrderRequest;
import com.todaysoft.ghealth.agcy.request.QueryOrdersRequest;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.OrderUploadRequest;
import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.mgmt.model.ReportGenerateTaskDTO;
import com.todaysoft.ghealth.mgmt.request.DownloadOrderReportRequest;
import com.todaysoft.ghealth.mgmt.request.MaintainOrderUploadRequest;
import com.todaysoft.ghealth.model.*;
import com.todaysoft.ghealth.model.product.AgencyProductDetails;
import com.todaysoft.ghealth.model.product.TestingItem;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.wrapper.OrderWrapper;
import com.todaysoft.ghealth.service.wrapper.ReportGenerateTaskWrapper;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.utils.ImportExcel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class OrderService implements IOrderService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private OrderWrapper orderWrapper;
    
    @Autowired
    private ReportGenerateTaskWrapper reportGenerateTaskWrapper;
    
    @Autowired
    private AgencyProductService agencyProductService;
    
    @Override
    public Pagination<Order> search(OrderSearcher searcher, int pageNo, int pageSize)
    {
        QueryOrdersRequest request = searcherWarp(searcher);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Order> response =
            gateway.request("/agcy/order/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Order>>()
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
    public OrderDetails getOrder(String id)
    {
        OrderDetails order = new OrderDetails();
        return order;
    }
    
    @Override
    public void create(Order data)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        BeanUtils.copyProperties(data, request, "customer", "product");
        request.setCustomer(data.getCustomer());
        request.setProduct(data.getProduct());
        gateway.request("/agcy/order/create", request);
    }
    
    @Override
    public void modify(Order data)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        BeanUtils.copyProperties(data, request, "customer", "product");
        request.setCustomer(data.getCustomer());
        request.setProduct(data.getProduct());
        request.setAgencyId(data.getAgency().getId());
        gateway.request("/agcy/order/modify", request);
    }
    
    @Override
    public void cancel(Order data)
    {
        for (String id : data.getId().split("-"))
        {
            MaintainOrderRequest request = new MaintainOrderRequest();
            request.setId(id);
            request.setCustomer(data.getCustomer());
            request.setProduct(data.getProduct());
            gateway.request("/agcy/order/cancel", request);
        }
    }
    
    @Override
    public void place(Order data)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        BeanUtils.copyProperties(data, request, "customer", "product");
        request.setCustomer(data.getCustomer());
        request.setProduct(data.getProduct());
        gateway.request("/agcy/order/place", request);
    }
    
    @Override
    public Order getOrderById(String id)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Order> response =
            gateway.request("/agcy/order/getById", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        if (null == response.getData())
        {
            return new Order();
        }
        return warp(response.getData());
    }
    
    @Override
    public List<Order> list(OrderSearcher searcher)
    {
        QueryOrdersRequest request = searcherWarp(searcher);
        ListResponse<com.todaysoft.ghealth.base.response.model.Order> response =
            gateway.request("/agcy/order/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        List<Order> orders = new ArrayList<Order>();
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return orders;
        }
        return orderWrapper.wrap(response.getData());
    }
    
    @Override
    public List<OrderHistory> getOrderHistoriesByOrderId(String orderId)
    {
        com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest request = new com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest();
        request.setId(orderId);
        ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory> response = gateway.request("/agcy/order/getOrderHistoriesByOrderId",
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
    public ReportGenerateTask getReportGenerateTask(String reportGenerateTaskId)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(reportGenerateTaskId);
        
        ObjectResponse<ReportGenerateTaskDTO> response =
            gateway.request("/agcy/order/report/generate/details", request, new ParameterizedTypeReference<ObjectResponse<ReportGenerateTaskDTO>>()
            {
            });
        return reportGenerateTaskWrapper.wrap(response.getData());
    }
    
    @Override
    public OrderReportStreamDTO getReport(String id, String type)
    {
        DownloadOrderReportRequest request = new DownloadOrderReportRequest();
        request.setOrderId(id);
        request.setType(type);
        request.setLoginType("0");
        
        ObjectResponse<OrderReportStreamDTO> response =
            gateway.request("/agcy/order/report/stream", request, new ParameterizedTypeReference<ObjectResponse<OrderReportStreamDTO>>()
            {
            });
        return response.getData();
    }
    
    @Override
    public void createOrderAtMobile(Order data)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        BeanUtils.copyProperties(data, request, "customer", "product");
        request.setCustomer(data.getCustomer());
        request.setProduct(data.getProduct());
        request.setAgencyId(data.getAgency().getId());
        request.setLogin(false);
        gateway.request("/agcy/order/createOrderAtMobile", request);
    }
    
    @Override
    public void upload(List<OrderUploadRequest> datas)
    {
        MaintainOrderUploadRequest request = new MaintainOrderUploadRequest();
        request.setList(datas);
        gateway.request("/agcy/order/createOrders", request);
    }

    @Override
    public void delete(String id)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setId(id);
        gateway.request("/agcy/order/delete", request);
    }

    @Override
    public OrderUploadData parse(MultipartFile uploadData)
    {
        OrderUploadData orderUploadData = new OrderUploadData();
        List<String> heads = new ArrayList<>();
        List<Map<String, OrderUploadModel>> list = new ArrayList<>();
        Integer uploadCount = 0;
        Integer invalidUploadCount = 0;
        OrderUploadModel model;
        ImportExcel ei;
        String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);

        try
        {
            ei = new ImportExcel(uploadData, 0, 0);
            uploadCount = ei.getLastDataRowNum();
            for (int i = 0; i <= ei.getLastDataRowNum(); i++)
            {
                Map<String, OrderUploadModel> map = new HashMap<>();
                boolean flag = true;
                for (int j = 0; j < ei.getLastCellNum(); j++)
                {
                    model = new OrderUploadModel();
                    String data = (String)ei.getCellValue(ei.getRow(i), j);
                    if (i == 0)
                    {
                        heads.add(data);
                    }
                    else
                    {
                        if (j == 0)
                        {
                            data = data.trim();
                            //订单编号
                            if (StringUtils.isEmpty(data))
                            {
                                break;
//                                setEmptyData(data, model);
                            }
                            else
                            {
                                model.setText(data);
                                model.setMach(isUniqueCode(null, data));
                            }
                        }
                        else if (j == 1 || j == 2)
                        {
                            //客户姓名  性别
                            setEmptyData(data, model);
                        }
                        else if (j == 4)
                        {
                            //出生年月
                            if (StringUtils.isEmpty(data)) {
                                model.setMach(true);
                            } else {
                                if (pattern.matcher(data).matches()) {
                                    model.setMach(true);
                                }
                            }
                            model.setText(ei.getCellValue(ei.getRow(i), j));

                        }
                        else if (j == 5)
                        {
                            if (StringUtils.isEmpty(data))
                            {
                                model.setMach(true);
                                model.setText("***");
                            }
                            else
                            {
                                model.setMach(true);
                                model.setText(ei.getCellValue(ei.getRow(i), j));
                            }
                        }
                        else if (j == 6)
                        {
                            //产品编号
                            if (StringUtils.isEmpty(data))
                            {
                                setEmptyData(data, model);
                            }
                            else
                            {
                                String relProductCode = null;
                                if (data.indexOf("/") != -1)
                                {
                                    relProductCode = data.substring(0, data.indexOf("/"));
                                }
                                else
                                {
                                    relProductCode = data;
                                }
                                
                                model.setMach(agencyProductService.isUniqueCode(relProductCode));
                                model.setText(relProductCode);
                            }
                        }
                        else
                        {
                            model.setMach(true);
                            model.setText(ei.getCellValue(ei.getRow(i), j));
                        }
                        if (flag && !model.getIsMach())
                        {
                            invalidUploadCount++;
                            flag = false;
                        }
                        map.put(heads.get(j), model);
                    }
                }
                
                if (!CollectionUtils.isEmpty(map))
                {
                    list.add(map);
                }
            }
        }
        catch (InvalidFormatException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        orderUploadData.setHeads(heads);
        orderUploadData.setDatas(list);
        orderUploadData.setUploadCount(uploadCount);
        orderUploadData.setInvalidUploadCount(invalidUploadCount);
        orderUploadData.setEffectiveUploadCount(uploadCount - invalidUploadCount);
        return orderUploadData;
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
    public Boolean isUniqueCode(String id, String code)
    {
        QueryOrdersRequest request = new QueryOrdersRequest();
        request.setOrderCode(code);
        request.setId(id);
        request.setLogin(false);
        ObjectResponse<Boolean> response = gateway.request("/agcy/order/isUniqueCode", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }
    
    private Order warp(com.todaysoft.ghealth.base.response.model.Order result)
    {
        Order order = new Order();
        if (null == result)
        {
            return order;
        }
        if (null != result.getProduct())
        {
            AgencyProductDetails data = new AgencyProductDetails();
            BeanUtils.copyProperties(result.getProduct(), data, "createTime", "updateTime", "deleteTime");
            order.setAgencyProductDetails(data);
            if (!CollectionUtils.isEmpty(result.getProduct().getTestingItems()))
            {
                List<TestingItem> list = new ArrayList<>();
                for (com.todaysoft.ghealth.base.response.model.TestingItem item : result.getProduct().getTestingItems())
                {
                    list.add(new TestingItem(item.getId(), item.getCode(), item.getName()));
                }
                data.setTestingItems(list);
            }
            order.setAgencyProductDetails(data);
        }
        if (null != result.getCustomer())
        {
            order.setCustomer(result.getCustomer());
        }
        BeanUtils.copyProperties(result, order, "createTime", "updateTime", "deleteTime");
        order.setCreateTime(null == result.getCreateTime() ? null : new Date(result.getCreateTime()));
        order.setSubmitTime(null == result.getSubmitTime() ? null : new Date(result.getSubmitTime()));
        return order;
    }
    
    private QueryOrdersRequest searcherWarp(OrderSearcher searcher)
    {
        QueryOrdersRequest request = new QueryOrdersRequest();
        BeanUtils.copyProperties(searcher, request, "startTime", "endTime","startReportTime","endReportTime");
        if (null != searcher.getStartTime())
        {
            request.setStartCreateTime(String.valueOf(searcher.getStartTime().getTime()));
        }
        if (null != searcher.getEndTime())
        {
            request.setEndStartTime(String.valueOf(searcher.getEndTime().getTime()));
        }
        if (null != searcher.getStartReportTime())
        {
            request.setStartReportTime(String.valueOf(searcher.getStartReportTime().getTime()));
        }
        if (null != searcher.getEndReportTime())
        {
            request.setEndReportTime(String.valueOf(searcher.getEndReportTime().getTime()));
        }
        return request;
    }
    
    private void setEmptyData(String data, OrderUploadModel model)
    {
        model.setMach(!StringUtils.isEmpty(data));
        model.setText(StringUtils.isEmpty(data) ? "空白字符" : data);
    }
}
