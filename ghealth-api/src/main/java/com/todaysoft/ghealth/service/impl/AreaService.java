package com.todaysoft.ghealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todaysoft.ghealth.mybatis.mapper.AreaMapper;
import com.todaysoft.ghealth.mybatis.model.Area;
import com.todaysoft.ghealth.service.IAreaService;

@Service
public class AreaService implements IAreaService
{
    @Autowired
    private AreaMapper mapper;
    
    private Map<String, Area> cache = new HashMap<String, Area>();
    
    @Override
    public List<Area> findProvince()
    {
        return mapper.findProvince();
    }
    
    @Override
    public List<Area> findByParentId(String parentId)
    {
        return mapper.findByParentId(String.valueOf(parentId));
    }
    
    @Override
    public String getDistrictName(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            return "";
        }
        
        Area district = cache.get(id);
        
        if (null == district)
        {
            district = mapper.get(id);
            
            if (null != district)
            {
                cache.put(id, district);
            }
        }
        
        return null == district ? "" : district.getName();
    }
    
    @Override
    public String getDistrictFullName(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            return "";
        }
        
        Area district = cache.get(id);
        
        if (null == district)
        {
            district = mapper.get(id);
            
            if (null != district)
            {
                cache.put(id, district);
            }
        }
        
        return null == district ? "" : district.getFullName();
    }
}
