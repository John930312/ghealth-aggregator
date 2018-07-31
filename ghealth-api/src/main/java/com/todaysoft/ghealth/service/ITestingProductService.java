package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.mybatis.searcher.ProductSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface ITestingProductService extends PagerQueryHandler<Product>
{
    Product get(String id);
    
    void create(Product product, List<String> testingItems);
    
    void modify(Product product, List<String> testingItems);
    
    boolean isCodeUnique(String id, String code);

    List<Product> list(ProductSearcher searchder);
}
