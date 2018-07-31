package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.mybatis.searcher.ProductSearcher;

public interface TestingProductMapper
{
    int count(ProductSearcher searcher);
    
    List<Product> search(ProductSearcher searcher);
    
    Product get(String id);
    
    void insert(Product data);
    
    void update(Product data);
}
