package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.product.Product;
import com.todaysoft.ghealth.model.product.ProductDetails;

@Component
public class ProductWrapper
{
    @Autowired
    private TestingItemWrapper testingItemWrapper;
    
    public List<Product> wrap(List<com.todaysoft.ghealth.base.response.model.Product> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Product product;
        List<Product> products = new ArrayList<Product>();
        
        for (com.todaysoft.ghealth.base.response.model.Product record : records)
        {
            product = new Product();
            wrapRecord(record, product);
            products.add(product);
        }
        
        return products;
    }
    
    public ProductDetails wrap(com.todaysoft.ghealth.base.response.model.ProductDetails record)
    {
        if (null == record)
        {
            return null;
        }
        
        ProductDetails details = new ProductDetails();
        wrapDetails(record, details);
        return details;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Product source, Product target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
    }
    
    private void wrapDetails(com.todaysoft.ghealth.base.response.model.ProductDetails source, ProductDetails target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime", "testingItems");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
        target.setTestingItems(testingItemWrapper.warp(source.getTestingItems()));
    }
}
