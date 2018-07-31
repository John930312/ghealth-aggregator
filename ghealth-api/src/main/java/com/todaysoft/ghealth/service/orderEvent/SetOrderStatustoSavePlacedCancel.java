package com.todaysoft.ghealth.service.orderEvent;

import java.util.Date;
import java.util.Map;

import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.service.IOrderHistoryService;
import com.todaysoft.ghealth.utils.IdGen;

@Aspect
@Component
public class SetOrderStatustoSavePlacedCancel extends OrderEventAspectJ
{
    
    @Autowired
    private IOrderHistoryService orderHistoryService;
    
    @Autowired
    private IOrderService orderService;
    
    @Override
    public void setOrderStatusByMethodReturnString(JoinPoint point, Map<String, String> map, Object returnObj)
    {
        OrderHistory orderHistory = new OrderHistory();
        String orderId = null;
        String operatorName = getOperatorName(map, point);
        if (returnObj instanceof String)
        {
            orderId = (String)returnObj;
        }
        if (StringUtils.isEmpty(orderId))
        {
            return;
        }
        //修改
        if ("12".equals(map.get(POINT_EVENTTYPE)))
        {
            Order order = orderService.getOrderById(orderId);
            if ("6".equals(order.getStatus()))
            {
                return;
            }
        }
        orderHistory.setId(IdGen.uuid());
        orderHistory.setOrderId(orderId);
        orderHistory.setEventTime(new Date());
        orderHistory.setOperatorName(operatorName);
        setOrderEventStatus(map.get(POINT_EVENTTYPE), orderHistory);
        orderHistoryService.create(orderHistory);
    }
}
