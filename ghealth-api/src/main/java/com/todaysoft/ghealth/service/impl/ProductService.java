package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.ProductItemMapper;
import com.todaysoft.ghealth.mybatis.mapper.TestingProductMapper;
import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.mybatis.model.ProductItem;
import com.todaysoft.ghealth.mybatis.searcher.ProductSearcher;
import com.todaysoft.ghealth.service.ITestingProductService;

@Service
public class ProductService implements ITestingProductService
{
    @Autowired
    private TestingProductMapper mapper;
    
    @Autowired
    private ProductItemMapper productItemMapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof ProductSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((ProductSearcher)searcher);
    }
    
    @Override
    public List<Product> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof ProductSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            ProductSearcher tis = (ProductSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((ProductSearcher)searcher);
    }
    
    @Override
    public Product get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(Product product, List<String> testingItems)
    {
        mapper.insert(product);
        
        testingItems.stream().forEach(o -> {
            ProductItem pi = new ProductItem();
            pi.setProductId(product.getId());
            pi.setTestingItemId(o);
            productItemMapper.insertSelective(pi);
        });
    }
    
    @Override
    @Transactional
    public void modify(Product product, List<String> testingItems)
    {
        testingItems.stream().forEach(o -> {
            ProductItem pi = new ProductItem();
            pi.setProductId(product.getId());
            pi.setTestingItemId(o);
            productItemMapper.insertSelective(pi);
        });
        mapper.update(product);
    }
    
    @Override
    public boolean isCodeUnique(String id, String code)
    {
        ProductSearcher searcher = new ProductSearcher();
        searcher.setCode(code);
        searcher.setCodeExactMatches(true);
        
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        
        int count = mapper.count(searcher);
        return count == 0;
    }

    @Override
    public List<Product> list(ProductSearcher searchder)
    {
        return mapper.search(searchder);
    }
}
