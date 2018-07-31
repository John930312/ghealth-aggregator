package com.todaysoft.ghealth.service.wrapper.mobileWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.base.response.model.TestingItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MobileTestingItemWrapper
{
    public List<TestingItem> wrap(List<com.todaysoft.ghealth.mybatis.model.TestingItem> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public TestingItem wrap(com.todaysoft.ghealth.mybatis.model.TestingItem source)
    {
        TestingItem target = new TestingItem();
        wrap(source, target);
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.TestingItem source, TestingItem target)
    {
        BeanUtils.copyProperties(source, target, "submitTime", "createTime", "updateTime", "deleteTime");
    }
}
