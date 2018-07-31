package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.OrderHistoryMapper;
import com.todaysoft.ghealth.mybatis.mapper.SampleMapper;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderSignIn;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.ISampleService;
import com.todaysoft.ghealth.utils.DataStatus;
import com.todaysoft.ghealth.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SampleService implements ISampleService{
    @Autowired(required = false)
    private SampleMapper mapper;

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
    public void modifyStatus(Order data)
    {
        mapper.modifyStatus(data);
    }

    @Override
    public boolean isStatus(String id)
    {
        if(mapper.countById(id)==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Order getInformation(String orderCode)
    {
        return mapper.getInformation(orderCode);
    }

    @Override
    public void createOperate(OrderSignIn orderSignIn){
        mapper.createOperate(orderSignIn);
    }

    @Override
    @Transactional
    public Map<String,String> sampleOperation(String orderCodes, String status, String operateName){
        Map<String,String> map = new HashMap<>();
        String split = "-";
        int flag =0;
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
