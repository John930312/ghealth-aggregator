package com.todaysoft.ghealth.portal.mgmt.facade.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.mgmt.model.LocusGenetypeDTO;
import com.todaysoft.ghealth.mybatis.model.LocusGenetype;
import com.todaysoft.ghealth.service.ITestingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.dto.OrderSimpleDTO;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.service.wrapper.Wrapper;

@Component
public class OrderSimpleWrapper implements Wrapper<Order, OrderSimpleDTO>
{
    @Autowired
    private ITestingDataService testingDataService;
    
    @Override
    public List<OrderSimpleDTO> wrap(List<Order> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        OrderSimpleDTO target;
        List<OrderSimpleDTO> targets = new ArrayList<OrderSimpleDTO>();
        
        for (Order source : sources)
        {
            target = new OrderSimpleDTO();
            target.setId(source.getId());
            target.setCode(source.getCode());
            target.setStatus(source.getStatus());
            target.setVigilance(source.getVigilance());
            
            List<LocusGenetype> orderTestingData = testingDataService.getOrderTestingData(target.getId());
            if (!CollectionUtils.isEmpty(orderTestingData))
            {
                List<LocusGenetypeDTO> dtos = orderTestingData.stream().map(x -> {
                    LocusGenetypeDTO dto = new LocusGenetypeDTO();
                    dto.setGenetype(x.getGenetype());
                    dto.setLocus(x.getLocus());
                    return dto;
                }).collect(Collectors.toList());
                target.setLocusGenetypeDTOS(dtos);
            }
            targets.add(target);
        }
        
        return targets;
    }
}
