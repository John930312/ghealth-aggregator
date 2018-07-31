package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.agcy.request.QueryAreaRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Area;
import com.todaysoft.ghealth.service.IAreaService;

@Service
public class AreaService implements IAreaService
{
    @Autowired
    private Gateway gateway;
    
    @Override
    public List<Area> findProvince()
    {
        QueryAreaRequest request = new QueryAreaRequest();
        ListResponse<Area> response = gateway.request("/agcy/area/findProvince", request, new ParameterizedTypeReference<ListResponse<Area>>()
        {
        });
        
        return response.getData();
    }
    
    @Override
    public List<com.todaysoft.ghealth.base.response.model.Area> findByParentId(String parentId)
    {
        QueryAreaRequest request = new QueryAreaRequest();
        request.setParentId(parentId);
        ListResponse<com.todaysoft.ghealth.base.response.model.Area> response =
            gateway.request("/agcy/area/findByParentId", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Area>>()
            {
            });
        
        return response.getData();
    }
}
