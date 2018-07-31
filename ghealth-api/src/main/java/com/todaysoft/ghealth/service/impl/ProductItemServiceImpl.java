package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.mybatis.mapper.ProductItemMapper;
import com.todaysoft.ghealth.service.IProductItemService;

@Service
public class ProductItemServiceImpl implements IProductItemService
{
    @Autowired
    private ProductItemMapper productItemMapper;
    
    @Override
    public List<String> getItemsByProductId(String id)
    {
        return productItemMapper.getItemsByProductId(id);
    }
    
    @Override
    public void deleteItemsByProductId(String id)
    {
        productItemMapper.deleteItemsByProductId(id);
    }

    @Override
    public List<String> getProductIdByItemId(String id)
    {
        return productItemMapper.getProductIdByItemId(id);
    }
    
}
