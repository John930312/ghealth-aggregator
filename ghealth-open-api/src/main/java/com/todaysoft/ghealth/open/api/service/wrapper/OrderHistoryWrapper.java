package com.todaysoft.ghealth.open.api.service.wrapper;

import com.todaysoft.ghealth.open.api.mybatis.model.Agency;
import com.todaysoft.ghealth.open.api.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.open.api.restful.model.AgencyDTO;
import com.todaysoft.ghealth.open.api.restful.model.OrderHistoryDTO;
import org.springframework.stereotype.Component;

/**
 * @Author: xjw
 * @Date: 2018/8/29 9:21
 */
@Component
public class OrderHistoryWrapper extends Wrapper<OrderHistory, OrderHistoryDTO>
{
    
    @Override
    protected String[] getIgnoreProperties()
    {
        return new String[] {"eventTime"};
    }
    
    @Override
    protected void setIgnoreProperties(OrderHistory source, OrderHistoryDTO target)
    {
        target.setEventTime(format(source.getEventTime()));
    }
}
