package com.todaysoft.ghealth.service.orderEvent;

import java.util.Date;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.service.IOrderHistoryService;
import com.todaysoft.ghealth.utils.IdGen;

@Aspect
@Component
public class SetOrderStatusToSignSend extends OrderEventAspectJ
{
    @Autowired
    private IOrderHistoryService orderHistoryService;
    
    @Override
    public void setOrderStatusByMethodReturnMap(JoinPoint point, Map<String, String> handlerMap, Object returnObj)
    {
        String operatorName = getOperatorName(handlerMap, point);
        
        if (returnObj instanceof Map)
        {
            Map<String, String> map = (Map<String, String>)returnObj;
            if (!map.isEmpty())
            {
                for (Map.Entry<String, String> entry : map.entrySet())
                {
                    OrderHistory orderHistory = new OrderHistory();
                    orderHistory.setOrderId(entry.getKey());
                    orderHistory.setOperatorName(operatorName);
                    orderHistory.setEventDetails(map.get(entry.getKey()));
                    orderHistory.setEventTime(new Date());
                    orderHistory.setId(IdGen.uuid());
                    setOrderEventStatus(handlerMap.get(POINT_EVENTTYPE), orderHistory);
                    orderHistoryService.create(orderHistory);
                }
            }
        }
    }
}
