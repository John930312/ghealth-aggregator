package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Area;
import com.todaysoft.ghealth.mgmt.request.QueryAreaRequest;
import com.todaysoft.ghealth.service.IAreaService;

@Component
public class AreaMgmtFacade
{
    @Autowired
    private IAreaService service;
    
    public ListResponse<Area> list(QueryAreaRequest request)
    {
        
        List<com.todaysoft.ghealth.mybatis.model.Area> datas;
        
        if (null != request.getParentId())
        {
            datas = service.findByParentId(request.getParentId());
        }
        else
        {
            datas = service.findProvince();
        }
        List<com.todaysoft.ghealth.base.response.model.Area> areas = new ArrayList<>();
        com.todaysoft.ghealth.base.response.model.Area area;
        if (null != datas && datas.size() > 0)
        {
            for (com.todaysoft.ghealth.mybatis.model.Area data : datas)
            {
                
                area = new com.todaysoft.ghealth.base.response.model.Area();
                BeanUtils.copyProperties(data, area);
                areas.add(area);
                
            }
        }
        return new ListResponse<com.todaysoft.ghealth.base.response.model.Area>(areas);
        
    }
    
}
