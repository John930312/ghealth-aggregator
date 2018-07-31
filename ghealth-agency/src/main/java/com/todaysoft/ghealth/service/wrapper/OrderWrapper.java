package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.ProductDetails;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderHistory;
import com.todaysoft.ghealth.utils.DataStatus;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class OrderWrapper
{
    public List<Order> wrap(List<com.todaysoft.ghealth.base.response.model.Order> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Order order;
        List<Order> orders = new ArrayList<Order>();
        for (com.todaysoft.ghealth.base.response.model.Order record : records)
        {
            order = new Order();
            wrapRecord(record, order);
            orders.add(order);
        }
        return orders;
    }
    
    public Order wrap(com.todaysoft.ghealth.base.response.model.Order record)
    {
        if (null == record)
        {
            return null;
        }
        
        Order order = new Order();
        wrapRecord(record, order);
        return order;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Order source, Order target)
    {
        
        if (null != source.getProduct())
        {
            ProductDetails data = new ProductDetails();
            BeanUtils.copyProperties(source.getProduct(), data, "createTime", "updateTime", "deleteTime");
            target.setProduct(data);
        }
        if (null != source.getCustomer())
        {
            target.setCustomer(source.getCustomer());
        }
        if (null != source.getAgency())
        {
            target.setAgency(source.getAgency());
        }
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime","submitTime","reportGenerateTime","operateTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setReportGenerateTime(null == source.getReportGenerateTime() ? null : new Date(source.getReportGenerateTime()));
        target.setSubmitTime(null == source.getSubmitTime() ? null : new Date(source.getSubmitTime()));
        if (!Objects.isNull(target.getSubmitTime())&& DataStatus.ORDER_PLACED.equals(target.getStatus()))
        {
            Date monthLater = DateUtils.addMonths(target.getSubmitTime(), 1);

            if (System.currentTimeMillis() > monthLater.getTime())
            {
                target.setMonthAgo(true);
            }
        }
        target.setOperateTime(null == source.getOperateTime() ? null : new Date(source.getOperateTime()));
    }



    public List<OrderHistory> wrapOrderHistory(List<com.todaysoft.ghealth.base.response.model.OrderHistory> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        OrderHistory OrderHistory;
        List<OrderHistory> OrderHistorys = new ArrayList<OrderHistory>();
        for (com.todaysoft.ghealth.base.response.model.OrderHistory record : records)
        {
            OrderHistory = new OrderHistory();
            wrapOrderHistory(record, OrderHistory);
            OrderHistorys.add(OrderHistory);
        }
        return OrderHistorys;
    }

    public OrderHistory wrapOrderHistory(com.todaysoft.ghealth.base.response.model.OrderHistory record)
    {
        if (null == record)
        {
            return null;
        }

        OrderHistory OrderHistory = new OrderHistory();
        wrapOrderHistory(record, OrderHistory);
        return OrderHistory;
    }

    private void wrapOrderHistory(com.todaysoft.ghealth.base.response.model.OrderHistory source, OrderHistory target)
    {
        BeanUtils.copyProperties(source, target, "eventTime");
        target.setEventTime(null == source.getEventTime() ? null : new Date(source.getEventTime()));
    }
}
