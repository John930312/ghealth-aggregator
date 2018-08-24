package com.todaysoft.ghealth.service.wrapper;

import java.util.*;

import com.todaysoft.ghealth.mybatis.mapper.ReportGenerateTaskMapper;
import com.todaysoft.ghealth.mybatis.mapper.SampleDeliveryDetailsMapper;
import com.todaysoft.ghealth.mybatis.mapper.SampleDeliveryMapper;
import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.ReportGenerateTask;
import com.todaysoft.ghealth.mybatis.model.SampleDelivery;
import com.todaysoft.ghealth.mybatis.model.SampleDeliveryDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.base.response.model.OrderHistory;
import com.todaysoft.ghealth.portal.mgmt.facade.TestingProductMgmtFacade;
import com.todaysoft.ghealth.service.IAgencyService;

@Component
public class OrderWrapper
{
    @Autowired
    private CustomerWrapper customerWrapper;
    
    @Autowired
    private AgencyWrapper agencyWrapper;
    
    @Autowired
    private TestingProductMgmtFacade facade;
    
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private SampleDeliveryDetailsMapper sampleDeliveryDetailsMapper;
    
    @Autowired
    private SampleDeliveryMapper sampleDeliveryMapper;
    
    @Autowired
    private ReportGenerateTaskMapper reportGenerateTaskMapper;
    
    public List<Order> wrap(List<com.todaysoft.ghealth.mybatis.model.Order> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Order order;
        List<Order> orders = new ArrayList<Order>();
        
        for (com.todaysoft.ghealth.mybatis.model.Order record : records)
        {
            order = new Order();
            wrap(record, order);
            orders.add(order);
        }
        
        return orders;
    }
    
    public Order wrap(com.todaysoft.ghealth.mybatis.model.Order record)
    {
        Order order = new Order();
        wrap(record, order);
        return order;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Order source, Order target)
    {
        if (null != source.getCustomer())
        {
            target.setCustomer(customerWrapper.wrap(source.getCustomer()));
        }
        
        if (null != source.getProduct())
        {
            QueryDetailsRequest request = new QueryDetailsRequest();
            request.setId(source.getProduct().getId());
            target.setProduct(facade.get(request));
        }
        
        if (null != source.getAgency())
        {
            Agency agency = agencyService.get(source.getAgency().getId());
            if (null != agency)
            {
                target.setAgency(agencyWrapper.wrap(agency));
            }
        }
        
        MutableBoolean havePdf = new MutableBoolean(false);
        MutableBoolean haveWord = new MutableBoolean(false);
        Optional.ofNullable(source.getReportGenerateTaskId()).ifPresent(x -> {
            ReportGenerateTask reportGenerateTask = reportGenerateTaskMapper.get(x);
            Optional.ofNullable(reportGenerateTask).ifPresent(y -> {
                havePdf.setValue(StringUtils.isNotEmpty(y.getPdfFileUrl()));
                haveWord.setValue(StringUtils.isNotEmpty(y.getWordFileUrl()));
            });
        });
        
        target.setHavePDF(havePdf.getValue());
        target.setHaveWord(haveWord.getValue());
        
        List<SampleDeliveryDetails> datas = sampleDeliveryDetailsMapper.getByOrderId(source.getId());
        SampleDeliveryDetails sampleDeliveryDetails = null;
        if (!CollectionUtils.isEmpty(datas))
        {
            sampleDeliveryDetails = datas.get(0);
            SampleDelivery sampleDelivery = sampleDeliveryMapper.getByBeliveryRecordId(sampleDeliveryDetails.getDeliveryRecordId());
            Optional.ofNullable(sampleDelivery).ifPresent(y -> {
                target.setOperateTime(null == y.getOperateTime() ? null : y.getOperateTime().getTime());
            });
        }
        
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setActualPrice(source.getActualPrice());
        target.setStatus(source.getStatus());
        target.setVigilance(source.getVigilance());
        target.setReportPrintRequired(source.getReportPrintRequired());
        target.setSampleType(source.getSampleType());
        target.setCreatorName(source.getCreatorName());
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setSubmitTime(null == source.getSubmitTime() ? null : source.getSubmitTime().getTime());
        target.setReportGenerateTime(null == source.getReportGenerateTime() ? null : source.getReportGenerateTime().getTime());
        target.setReportDownloadCount(source.getReportDownloadCount());
        target.setReportGenerateTaskId(source.getReportGenerateTaskId());
        if (null != source.getDataDetails())
        {
            target.setDataDetails(source.getDataDetails());
        }
        
    }
    
    public List<OrderHistory> wrapOrderHistory(List<com.todaysoft.ghealth.mybatis.model.OrderHistory> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        OrderHistory OrderHistory;
        List<OrderHistory> OrderHistorys = new ArrayList<OrderHistory>();
        for (com.todaysoft.ghealth.mybatis.model.OrderHistory record : records)
        {
            OrderHistory = new OrderHistory();
            wrapOrderHistory(record, OrderHistory);
            OrderHistorys.add(OrderHistory);
        }
        
        return OrderHistorys;
    }
    
    public OrderHistory wrapOrderHistory(com.todaysoft.ghealth.mybatis.model.OrderHistory record)
    {
        OrderHistory OrderHistory = new OrderHistory();
        wrapOrderHistory(record, OrderHistory);
        return OrderHistory;
    }
    
    private void wrapOrderHistory(com.todaysoft.ghealth.mybatis.model.OrderHistory source, OrderHistory target)
    {
        BeanUtils.copyProperties(source, target, "eventTime");
        target.setEventTime(null == source.getEventTime() ? null : source.getEventTime().getTime());
    }
}
