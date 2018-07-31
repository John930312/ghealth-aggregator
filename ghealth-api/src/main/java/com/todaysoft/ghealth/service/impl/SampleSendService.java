package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.OrderHistoryMapper;
import com.todaysoft.ghealth.mybatis.mapper.SampleSendMapper;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderSignIn;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.ISampleSendService;
import com.todaysoft.ghealth.utils.DataStatus;
import com.todaysoft.ghealth.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SampleSendService implements ISampleSendService{
    @Autowired(required = false)
    private SampleSendMapper mapper;

    @Autowired(required = false)
    private OrderHistoryMapper orderHistoryMapper;
    @Override
    public List<Order> list(OrderSearcher searcher)
    {
        return mapper.search(searcher);
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
    @Transactional
    public  Map<String,String> sampleSendOperation(String orderCodes,String status,String operateName){
        String split = "-";
        int flag =0;
        Map<String,String> map = new HashMap<>();
        for (String code : orderCodes.split(split))
        {
            if(StringUtils.isNotEmpty(code)){
                com.todaysoft.ghealth.mybatis.model.Order data = new com.todaysoft.ghealth.mybatis.model.Order();
                data.setStatus(status);
                data.setCode(code);
                mapper.modifyStatus(data);
                flag=flag+1;

            }

        }
        if (flag!=0){
            OrderSignIn orderSignIn = new OrderSignIn();
            orderSignIn.setId(IdGen.uuid());
            orderSignIn.setOperatorName(operateName);
            orderSignIn.setOperateTime(new Date());
            orderSignIn.setSampleCount(flag);
            mapper.createOperate(orderSignIn);

            for (String code : orderCodes.split(split)){
                if(StringUtils.isNotEmpty(code)){
                    com.todaysoft.ghealth.mybatis.model.OrderDetails orderDetails =new com.todaysoft.ghealth.mybatis.model.OrderDetails();
                    orderDetails.setId(IdGen.uuid());
                    orderDetails.setOperateId(orderSignIn.getId());
                    com.todaysoft.ghealth.mybatis.model.Order order = mapper.getInfo(code);
                    orderDetails.setOrderId(order.getId());
                    mapper.createOrderDetails(orderDetails);
                    map.put(orderDetails.getOrderId(),orderDetails.getId());
                }
            }
        }
        return map;

    }

}
