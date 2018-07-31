package com.todaysoft.ghealth.wechat.service.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.wechat.model.OrderHistory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OrderHistoryWrapper
{
    public List<OrderHistory> wrap(List<com.todaysoft.ghealth.base.response.model.OrderHistory> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public OrderHistory wrap(com.todaysoft.ghealth.base.response.model.OrderHistory source)
    {
        OrderHistory target = new OrderHistory();
        wrap(source, target);
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.base.response.model.OrderHistory source, OrderHistory target)
    {
        BeanUtils.copyProperties(source, target, "eventTime");
        target.setEventTime(null == source.getEventTime() ? null : new Date(source.getEventTime()));
    }
}
