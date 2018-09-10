package com.todaysoft.ghealth.open.api.service.impl;

import com.todaysoft.ghealth.open.api.mybatis.mapper.AreaMapper;
import com.todaysoft.ghealth.open.api.mybatis.model.Area;
import com.todaysoft.ghealth.open.api.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: xjw
 * @Date: 2018/9/10 10:48
 */
@Service
public class AreaService implements IAreaService
{
    @Autowired
    private AreaMapper areaMapper;
    
    @Override
    public String getAreaName(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            return "";
        }
        Area area = areaMapper.get(id);
        
        return null == area ? "" : area.getName();
    }
    
}
