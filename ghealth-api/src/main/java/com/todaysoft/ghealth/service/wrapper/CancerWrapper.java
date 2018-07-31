package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.Cancer;

@Component
public class CancerWrapper
{
    public List<Cancer> wrap(List<com.todaysoft.ghealth.mybatis.model.Cancer> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Cancer cancer;
        List<Cancer> cancers = new ArrayList<Cancer>();
        for (com.todaysoft.ghealth.mybatis.model.Cancer record : records)
        {
            cancer = new Cancer();
            wrap(record, cancer);
            cancers.add(cancer);
        }
        
        return cancers;
    }
    
    public Cancer wrap(com.todaysoft.ghealth.mybatis.model.Cancer record)
    {
        Cancer cancer = new Cancer();
        wrap(record, cancer);
        return cancer;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Cancer source, Cancer target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
    }
    
}
