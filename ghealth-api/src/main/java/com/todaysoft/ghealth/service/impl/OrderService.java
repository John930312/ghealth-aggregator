package com.todaysoft.ghealth.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest;
import com.todaysoft.ghealth.mybatis.mapper.OrderMapper;
import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.AgencyBill;
import com.todaysoft.ghealth.mybatis.model.ObjectStorage;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.portal.mgmt.facade.AgencyBillFacade;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.impl.core.AliyunOSSHandler;
import com.todaysoft.ghealth.service.orderEvent.OrderEventLog;
import com.todaysoft.ghealth.utils.DataStatus;
import com.todaysoft.ghealth.utils.DateOperateUtils;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class OrderService implements IOrderService
{
    @Autowired
    private OrderMapper mapper;
    
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private AgencyBillFacade agencyBillFacade;
    
    @Autowired
    private AliyunOSSHandler aliyunOSSHandler;
    
    @Override
    public List<Order> list(OrderSearcher searcher)
    {
        return mapper.search(searcher);
    }
    
    @Override
    public List<Order> list(Set<String> codes)
    {
        if (CollectionUtils.isEmpty(codes))
        {
            return Collections.emptyList();
        }
        
        return mapper.getOrdersByCodes(codes);
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof OrderSearcher))
        {
            throw new IllegalArgumentException();
        }
        return mapper.count((OrderSearcher)searcher);
    }
    
    @Override
    public List<Order> query(Object searcher, int offset, int limit)
    {
        
        if (!(searcher instanceof OrderSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            OrderSearcher tis = (OrderSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        return mapper.search((OrderSearcher)searcher);
    }
    
    @Override
    public Order getOrderById(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void modify(Order data)
    {
        mapper.modify(data);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        mapper.deleteByPrimaryKey(id);
    }
    
    @Override
    @Transactional
    public void create(Order data)
    {
        mapper.insertSelective(data);
    }
    
    @Override
    public List<String> getCodes()
    {
        return mapper.getCodes();
    }
    
    @Override
    public Boolean isUniqueCode(String id, String code)
    {
        OrderSearcher searcher = new OrderSearcher();
        searcher.setOrderCode(code);
        searcher.setOrderCodeExactMatches(true);
        searcher.setId(id);
        return mapper.count(searcher) == 0;
    }
    
    @Override
    public Order getOrderByTaskId(String taskId)
    {
        return mapper.getOrderByTaskId(taskId);
    }
    
    @Override
    @OrderEventLog(eventType = "6", handler = "mgmt")
    public String setOrderStautsSuccessed(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order data = getOrderByTaskId(request.getId());
        data.setStatus(DataStatus.ORDER_FINISHED);
        mapper.modify(data);
        return data.getId();
    }
    
    @Override
    public Order getByCode(String code)
    {
        return mapper.getByCode(code);
    }
    
    @Override
    @OrderEventLog(eventType = "11", handler = "mgmt")
    public String setOrderStautsDone(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order data = getOrderByTaskId(request.getId());
        data.setStatus(DataStatus.ORDER_FINISHED);
        mapper.modify(data);
        return data.getId();
    }
    
    @Override
    public String dataDetails(String orderId)
    {
        return mapper.getDataDetails(orderId);
    }
    
    @Override
    public boolean canModify(Order data)
    {
        boolean flag = true;
        String sourceProductId = getOrderById(data.getId()).getProduct().getId();
        if (sourceProductId.equals(data.getProduct().getId()))
        {
            //订单产品没有变动
            flag = false;
        }
        
        if (DataStatus.ORDER_DRAFT.equals(data.getStatus()))
        {
            //订单的状态为草稿
            flag = false;
        }
        return flag;
    }
    
    @Override
    public BigDecimal refundForOrderModify(Agency agency, MaintainOrderRequest request, Order data, String operateName)
    {
        BigDecimal amountBefore = agency.getAccountAmount();
        BigDecimal orderProductPrice = request.getPrice();
        BigDecimal amountAfter = amountBefore.add(orderProductPrice);
        AgencyBill backAgencyBill = new AgencyBill();
        backAgencyBill.setAgency(agency);
        backAgencyBill.setBillType(DataStatus.BILL_REFUND);
        backAgencyBill.setIncreased(DataStatus.BALANCE_PLUS);
        backAgencyBill.setAmountAfter(amountAfter);
        backAgencyBill.setAmountBefore(amountBefore);
        String sourceProductId = getOrderById(data.getId()).getProduct().getId();
        backAgencyBill.setEventDetails(MessageFormat.format("{0}-{1}", data.getId(), sourceProductId));
        backAgencyBill.setTitle("修改订单套餐退还" + orderProductPrice + "元");
        backAgencyBill.setBillTime(new Date());
        backAgencyBill.setOperateName(operateName);
        agencyBillFacade.create(backAgencyBill);
        agency.setAccountAmount(amountAfter);
        agencyService.modify(agency, null);
        return amountAfter;
    }
    
    @Override
    public void placeForOrderModify(BigDecimal amountAfter, Agency agency, MaintainOrderRequest request, Order data, String operateName)
    {
        BigDecimal accountAmountAfter = amountAfter.subtract(request.getActualPrice());
        AgencyBill agencyBill = new AgencyBill();
        agencyBill.setAgency(agency);
        agencyBill.setBillType(DataStatus.BILL_PLACE_ORDER);
        agencyBill.setIncreased(DataStatus.BALANCE_REDUCE);
        agencyBill.setAmountAfter(accountAmountAfter);
        agencyBill.setAmountBefore(amountAfter);
        agencyBill.setEventDetails(MessageFormat.format("{0}-{1}", data.getId(), data.getProduct().getId()));
        agencyBill.setTitle("修改订单套餐扣除" + request.getActualPrice() + "元");
        agencyBill.setOperateName(operateName);
        agencyBillFacade.create(agencyBill, DateOperateUtils.addOneSecond(new Date(), 1));
        agency.setAccountAmount(accountAmountAfter);
        agencyService.modify(agency, null);
    }
    
    @Override
    public Order getOrderDataByTaskId(String id)
    {
        return mapper.getOrderDataByTaskId(id);
    }
    
    @Override
    public List<String> orderIdList(OrderSearcher searcher)
    {
        return mapper.orderIdList(searcher);
    }
    
    @Override
    public List<Order> getOrdersByCustomerPhone(String phone)
    {
        return mapper.getOrdersByCustomerPhone(phone);
    }
    
    @Override
    public byte[] getPdfReportContents(String id) throws IOException
    {
        ObjectStorage objectStorage = mapper.getPdfReportUrl(id);
        
        if (Objects.isNull(objectStorage))
        {
            return null;
        }
        
        Map<String, String> attributes = JsonUtils.fromJson(objectStorage.getStorageDetails(), new TypeReference<Map<String, String>>()
        {
        });
        
        if (ObjectStorage.STORAGE_LOCAL.equals(objectStorage.getStorageType()))
        {
            String path =
                ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getServletContext().getRealPath(attributes.get("uri"));
            return IOUtils.toByteArray(new FileInputStream(new File(path)));
        }
        else
        {
            return aliyunOSSHandler.download(attributes.get("endpoint"), attributes.get("bucketName"), attributes.get("objectKey"));
        }
        
    }
    
    @Override
    public List<String> getLoci(String id)
    {
        return mapper.getLoci(id);
    }
    
    @Override
    public Pager<Order> getSpecialPager(OrderSearcher searcher, int pageNo, int pageSize)
    {
        Set<String> codeList = new HashSet<>();
        List<String> orderCodes = mapper.getOrderCodes();
        for (String codes : orderCodes)
        {
            List<String> list = Arrays.asList(codes.split(","));
            for (String code : list)
            {
                codeList.add(code);
            }
        }
        List<Order> orders = mapper.getOrderDetailsByCodes(codeList);
        orders = getFilterDatas(orders, searcher);
        
        Map<String, List<Order>> map = orders.stream().parallel().collect(groupingBy(x -> x.getCustomer().getName()));
        map.forEach(
            (k, v) -> v = v.stream().parallel().sorted(Comparator.<Order> comparingLong(x -> x.getSubmitTime().getTime()).reversed()).collect(toList()));
        List<List<Order>> mapList = new ArrayList<>();
        map.forEach((k, v) -> mapList.add(v));
        List<List<Order>> collect =
            mapList.stream().sorted(Comparator.<List<Order>> comparingLong(x -> x.get(0).getSubmitTime().getTime()).reversed()).collect(toList());
        List<Order> collect1 = collect.stream().flatMap(Collection::stream).collect(toList());
        
        //分页
        int count = collect1.size();
        if (count <= 0)
        {
            return Pager.empty(pageNo, pageSize);
        }
        int minPageNo = 1;
        int maxPageNo = count / pageSize;
        
        if (maxPageNo == 0 || count % pageSize != 0)
        {
            maxPageNo++;
        }
        
        pageNo = pageNo < minPageNo ? minPageNo : pageNo;
        pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
        List<Order> orderPage = new ArrayList<Order>();
        int currIdx = (pageNo > 1 ? (pageNo - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < collect1.size() - currIdx; i++)
        {
            Order order = collect1.get(currIdx + i);
            orderPage.add(order);
        }
        Pager<Order> pagers = new Pager<Order>();
        pagers.setPageNo(pageNo);
        pagers.setPageSize(pageSize);
        pagers.setTotalCount(count);
        pagers.setRecords(orderPage);
        return pagers;
    }
    
    @Override
    public String vigilanceList(OrderSearcher searcher)
    {
        return mapper.getVigilanceList(searcher);
    }
    
    private List<Order> getFilterDatas(List<Order> orders, OrderSearcher searcher)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        if (!StringUtils.isEmpty(searcher.getProductName()))
        {
            List<Order> list = orders.stream().filter(x -> x.getProduct().getName().contains(searcher.getProductName())).collect(toList());
            getFilterOrders(orders, list);
        }
        if (!StringUtils.isEmpty(searcher.getOrderCode()))
        {
            List<Order> list = orders.stream().filter(x -> x.getCode().contains(searcher.getOrderCode())).collect(toList());
            getFilterOrders(orders, list);
        }
        if (!StringUtils.isEmpty(searcher.getAgencyName()))
        {
            List<Order> list = orders.stream().filter(x -> x.getAgency().getName().contains(searcher.getAgencyName())).collect(toList());
            getFilterOrders(orders, list);
        }
        if (!StringUtils.isEmpty(searcher.getCustomerName()))
        {
            List<Order> list = orders.stream().filter(x -> x.getCustomer().getName().contains(searcher.getCustomerName())).collect(toList());
            getFilterOrders(orders, list);
        }
        if (!StringUtils.isEmpty(searcher.getCustomerPhone()))
        {
            List<Order> list = orders.stream().filter(x -> x.getCustomer().getPhone().contains(searcher.getCustomerPhone())).collect(toList());
            getFilterOrders(orders, list);
        }
        if (!StringUtils.isEmpty(searcher.getStartCreateTime()))
        {
            try
            {
                Date startTime = df.parse(searcher.getStartCreateTime());
                List<Order> list = orders.stream().filter(x -> !startTime.after(x.getSubmitTime())).collect(toList());
                getFilterOrders(orders, list);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        if (!StringUtils.isEmpty(searcher.getEndStartTime()))
        {
            try
            {
                Date endTime = df.parse(searcher.getEndStartTime());
                List<Order> list = orders.stream().filter(x -> !endTime.before(x.getSubmitTime())).collect(toList());
                getFilterOrders(orders, list);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return orders;
    }
    
    private List<Order> getFilterOrders(List<Order> filterOrders, List<Order> list)
    {
        filterOrders.clear();
        filterOrders.addAll(list);
        return filterOrders;
    }
    
}
