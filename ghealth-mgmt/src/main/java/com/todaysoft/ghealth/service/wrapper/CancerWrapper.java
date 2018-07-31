package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.cancer.Cancer;

@Component
public class CancerWrapper
{
    public List<Cancer> wrap(List<com.todaysoft.ghealth.base.response.model.Cancer> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Cancer cancer;
        List<Cancer> cancers = new ArrayList<Cancer>();
        for (com.todaysoft.ghealth.base.response.model.Cancer record : records)
        {
            cancer = new Cancer();
            wrapRecord(record, cancer);
            cancers.add(cancer);
        }
        return cancers;
    }
    
    public Cancer wrap(com.todaysoft.ghealth.base.response.model.Cancer record)
    {
        if (null == record)
        {
            return null;
        }
        
        Cancer cancer = new Cancer();
        wrapRecord(record, cancer);
        return cancer;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Cancer source, Cancer target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
    }
}
