package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.product.ProductAgent;

@Component
public class ProductAgentWrapper
{
    public List<ProductAgent> wrap(List<com.todaysoft.ghealth.base.response.model.AgencyProduct> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        ProductAgent productAgent;
        List<ProductAgent> customers = new ArrayList<ProductAgent>();
        
        for (com.todaysoft.ghealth.base.response.model.AgencyProduct record : records)
        {
            productAgent = new ProductAgent();
            wrapRecord(record, productAgent);
            customers.add(productAgent);
        }
        
        return customers;
    }
    
    public ProductAgent wrap(com.todaysoft.ghealth.base.response.model.AgencyProduct record)
    {
        if (null == record)
        {
            return null;
        }
        
        ProductAgent productAgent = new ProductAgent();
        wrapRecord(record, productAgent);
        return productAgent;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.AgencyProduct source, ProductAgent target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setProductCreateTime(null == source.getProductCreateTime() ? null : new Date(source.getProductCreateTime()));
        target.setProductUpdateTime(null == source.getProductUpdateTime() ? null : new Date(source.getProductUpdateTime()));
        target.setStartTime(null == source.getStartTime() ? null : new Date(source.getStartTime()));
        target.setEndTime(null == source.getEndTime() ? null : new Date(source.getEndTime()));
    }
}
