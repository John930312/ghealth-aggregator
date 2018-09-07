package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.mybatis.mapper.OrderHistoryMapper;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainpulatedDataFacade
{
    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    public void mainpulatedData()
    {
        OrderSearcher searcher = new OrderSearcher();
        searcher.setStatus("5");
        searcher.setStartCreateTime("2018-07-01 00:00:00");
        searcher.setOrderCodeExactMatches(false);
        List<Order> orderList = orderService.list(searcher);
        System.out.println("开始筛选两个月 共："+orderList.size()+"个订单");
        int count = 0;
        orderList.forEach(x->{
            if (StringUtils.isNotEmpty(x.getId())) {
                List<OrderHistory> orderHistoriesList = orderHistoryMapper.getOrderHistoriesByOrderIdAndFinish(x.getId());
                if (orderHistoriesList.size()>1)
                {
                    System.out.println("=======================================");
                    System.out.println(orderHistoriesList.get(0).getOrderId()+"重复订单数："+orderHistoriesList.size());
                    System.out.println(" ");
                    for (int i=0;i<orderHistoriesList.size()-1;i++){
                        OrderHistory orderHistory = orderHistoriesList.get(i);
                        orderHistoryMapper.delete(orderHistory.getId());
                    }
                }
            }
        });
        System.out.println("---------------------------------------------------");
        System.out.println("删除完成");
    }
}
