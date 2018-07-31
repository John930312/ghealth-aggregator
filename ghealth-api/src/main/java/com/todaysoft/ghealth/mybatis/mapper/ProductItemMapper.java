package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.ProductItem;

public interface ProductItemMapper
{
    
    void insertSelective(ProductItem pi);
    
    List<String> getItemsByProductId(String id);
    
    void deleteItemsByProductId(String id);

    List<String> getProductIdByItemId(String id);
    
}
