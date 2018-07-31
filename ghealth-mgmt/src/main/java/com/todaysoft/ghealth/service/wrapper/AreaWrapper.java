package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.Area;

@Component
public class AreaWrapper
{
    public List<Area> wrap(List<com.todaysoft.ghealth.base.response.model.Area> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Area area;
        List<Area> areas = new ArrayList<Area>();
        for (com.todaysoft.ghealth.base.response.model.Area record : records)
        {
            area = new Area();
            wrapRecord(record, area);
            areas.add(area);
        }
        return areas;
    }
    
    public Area wrap(com.todaysoft.ghealth.base.response.model.Area record)
    {
        if (null == record)
        {
            return null;
        }
        
        Area area = new Area();
        wrapRecord(record, area);
        return area;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Area source, Area target)
    {
        BeanUtils.copyProperties(source, target);
        
    }
}
