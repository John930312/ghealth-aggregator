package com.todaysoft.ghealth.service.wrapper.mobileWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.base.response.model.OrderHistory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MobileOrderHistoryWrapper
{
    public List<OrderHistory> wrap(List<com.todaysoft.ghealth.mybatis.model.OrderHistory> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public OrderHistory wrap(com.todaysoft.ghealth.mybatis.model.OrderHistory source)
    {
        OrderHistory target = new OrderHistory();
        wrap(source, target);
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.OrderHistory source, OrderHistory target)
    {
        BeanUtils.copyProperties(source, target, "eventTime");
        target.setEventTime(null == source.getEventTime() ? null : source.getEventTime().getTime());
    }
}
