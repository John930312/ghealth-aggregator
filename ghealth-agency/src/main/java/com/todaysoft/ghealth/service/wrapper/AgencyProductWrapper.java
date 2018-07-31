package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.model.product.AgencyProduct;
import com.todaysoft.ghealth.model.product.AgencyProductDetails;
import com.todaysoft.ghealth.model.product.TestingItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class AgencyProductWrapper
{
    public List<AgencyProduct> wrap(List<com.todaysoft.ghealth.base.response.model.AgencyProduct> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        List<AgencyProduct> targets = new ArrayList<>();
        sources.forEach(data -> {
            targets.add(wrap(data));
        });
        
        return targets;
    }
    
    public AgencyProduct wrap(com.todaysoft.ghealth.base.response.model.AgencyProduct source)
    {
        AgencyProduct target = new AgencyProduct();
        BeanUtils.copyProperties(source, target, "productCreateTime", "productUpdateTime");
        target.setProductCreateTime(null == source.getProductCreateTime() ? null : new Date(source.getProductCreateTime()));
        target.setProductUpdateTime(null == source.getProductUpdateTime() ? null : new Date(source.getProductUpdateTime()));
        return target;
    }
    
    public AgencyProductDetails wrap(com.todaysoft.ghealth.base.response.model.AgencyProductDetails data)
    {
        AgencyProductDetails details = new AgencyProductDetails();
        if (null == data)
        {
            return details;
        }
        BeanUtils.copyProperties(data, details, "productCreateTime", "productUpdateTime", "testingItems");
        details.setProductCreateTime(null == data.getProductCreateTime() ? null : new Date(data.getProductCreateTime()));
        details.setProductUpdateTime(null == data.getProductUpdateTime() ? null : new Date(data.getProductUpdateTime()));
        
        if (CollectionUtils.isEmpty(data.getTestingItems()))
        {
            details.setTestingItems(Collections.emptyList());
        }
        else
        {
            TestingItem testingItem;
            List<TestingItem> testingItems = new ArrayList<TestingItem>();
            
            for (com.todaysoft.ghealth.base.response.model.TestingItem record : data.getTestingItems())
            {
                testingItem = new TestingItem();
                BeanUtils.copyProperties(record, testingItem);
                testingItems.add(testingItem);
            }
            
            details.setTestingItems(testingItems);
        }
        return details;
    }
}
