package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.Product;
import com.todaysoft.ghealth.base.response.model.ProductDetails;

@Component
public class ProductWrapper
{
    public List<Product> wrap(List<com.todaysoft.ghealth.mybatis.model.Product> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Product product;
        List<Product> products = new ArrayList<Product>();
        
        for (com.todaysoft.ghealth.mybatis.model.Product record : records)
        {
            product = new Product();
            wrap(record, product);
            products.add(product);
        }
        
        return products;
    }
    
    public ProductDetails wrap(com.todaysoft.ghealth.mybatis.model.Product record)
    {
        ProductDetails details = new ProductDetails();
        wrap(record, details);
        return details;
    }
    
    public void wrap(com.todaysoft.ghealth.mybatis.model.Product source, Product target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
        

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
