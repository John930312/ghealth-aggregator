package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.model.item.TestingItem;

@Component
public class TestingItemWrapper
{
    public List<TestingItem> warp(List<com.todaysoft.ghealth.base.response.model.TestingItem> records)
    {
        List<TestingItem> items = new ArrayList<TestingItem>();
        
        if (null != records && records.size() > 0)
        {
            for (com.todaysoft.ghealth.base.response.model.TestingItem record : records)
            {
                TestingItem item = entityToPageModel(record);
                items.add(item);
            }
        }
        return items;
    }
    
    public TestingItem entityToPageModel(com.todaysoft.ghealth.base.response.model.TestingItem record)
    {
        TestingItem item = new TestingItem();
        BeanUtils.copyProperties(record, item, "createTime", "updateTime", "deleteTime");
        item.setCreateTime(null == record.getCreateTime() ? null : new Date(record.getCreateTime()));
        item.setUpdateTime(null == record.getUpdateTime() ? null : new Date(record.getUpdateTime()));
        item.setDeleteTime(null == record.getDeleteTime() ? null : new Date(record.getDeleteTime()));
        return item;
    }
    
}
