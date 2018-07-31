package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.TestingItem;

@Component
public class TestingItemWrapper
{
    public List<TestingItem> wrap(List<com.todaysoft.ghealth.mybatis.model.TestingItem> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        TestingItem testingItem;
        List<TestingItem> testingItems = new ArrayList<TestingItem>();
        
        for (com.todaysoft.ghealth.mybatis.model.TestingItem record : records)
        {
            testingItem = new TestingItem();
            wrap(record, testingItem);
            testingItems.add(testingItem);
        }
        
        return testingItems;
    }
    
    public void wrap(com.todaysoft.ghealth.mybatis.model.TestingItem source, TestingItem target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
        
        // TODO
        if ("1".equals(target.getCategory()))
        {
            target.setCategoryText("肿瘤风险");
        }
        else if ("2".equals(target.getCategory()))
        {
            target.setCategoryText("慢病");
        }
        else
        {
            target.setCategoryText("其他");
        }
        
        if ("0".equals(target.getSexRestraint()))
        {
            target.setSexRestraintText("通用");
        }
        else if ("1".equals(target.getSexRestraint()))
        {
            target.setSexRestraintText("男性");
        }
        else
        {
            target.setSexRestraintText("女性");
        }
    }
}
