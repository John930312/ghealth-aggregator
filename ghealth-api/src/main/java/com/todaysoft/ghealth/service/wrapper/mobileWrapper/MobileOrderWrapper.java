package com.todaysoft.ghealth.service.wrapper.mobileWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.mybatis.searcher.TestingItemSearcher;
import com.todaysoft.ghealth.service.IDictService;
import com.todaysoft.ghealth.service.IOrderHistoryService;
import com.todaysoft.ghealth.service.ITestingItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MobileOrderWrapper
{
    @Autowired
    private MobileCustomerWrapper mobileCustomerWrapper;
    
    @Autowired
    private MobileAgencyWrapper mobileAgencyWrapper;
    
    @Autowired
    private MobileProductDetailsWrapper mobileProductDetailsWrapper;
    
    @Autowired
    private MobileTestingItemWrapper mobileTestingItemWrapper;
    
    @Autowired
    private ITestingItemService testingItemService;
    
    @Autowired
    private IOrderHistoryService orderHistoryService;
    
    @Autowired
    private MobileOrderHistoryWrapper mobileOrderHistoryWrapper;
    
    @Autowired
    private IDictService dictService;
    
    public List<Order> wrap(List<com.todaysoft.ghealth.mybatis.model.Order> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public Order wrap(com.todaysoft.ghealth.mybatis.model.Order source)
    {
        Order target = new Order();
        wrap(source, target);
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Order source, Order target)
    {
        BeanUtils.copyProperties(source, target, "submitTime", "createTime", "updateTime", "deleteTime","deleted");
        String orderStatus = dictService.getText("ORDER_STATUS", source.getStatus());
        Optional.ofNullable(orderStatus).ifPresent(x -> target.setStatusText(x));
        String sampleType = dictService.getText("SAMPLE_TYPE", source.getSampleType());
        Optional.ofNullable(sampleType).ifPresent(x -> target.setSampleTypeText(x));
        String reportPrintRequired = dictService.getText("REPORT_PRINT_REQUIRED", String.valueOf(source.getReportPrintRequired()));
        Optional.ofNullable(reportPrintRequired).ifPresent(x -> target.setReportPrintRequiredText(x));
        Optional.ofNullable(source.getCustomer()).ifPresent(x -> target.setCustomer(mobileCustomerWrapper.wrap(x)));
        Optional.ofNullable(source.getAgency()).ifPresent(x -> target.setAgency(mobileAgencyWrapper.wrap(x)));
        
        Product product = source.getProduct();
        if (!Objects.isNull(product))
        {
            target.setProduct(mobileProductDetailsWrapper.wrap(product));
            TestingItemSearcher searcher = new TestingItemSearcher();
            searcher.setProductId(product.getId());
            List<TestingItem> testingItems = testingItemService.query(searcher, 0, -1);
            target.getProduct().setTestingItems(mobileTestingItemWrapper.wrap(testingItems));
        }
        
        List<OrderHistory> orderHistories = orderHistoryService.getOrderHistoriesByOrderId(source.getId());
        target.setOrderHistoryList(mobileOrderHistoryWrapper.wrap(orderHistories));
        
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setSubmitTime(null == source.getSubmitTime() ? null : source.getSubmitTime().getTime());
    }
}
