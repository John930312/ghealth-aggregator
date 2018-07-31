package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.model.apa.AgencyProduct;
import com.todaysoft.ghealth.model.product.Product;
import com.todaysoft.ghealth.model.product.ProductDetails;
import com.todaysoft.ghealth.model.product.TestingProductForm;
import com.todaysoft.ghealth.model.product.TestingProductSearcher;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface ITestingProductService
{
    Pagination<Product> search(TestingProductSearcher searcher, int pageNo, int pageSize);
    
    ProductDetails get(String id);
    
    boolean isCodeUnique(String id, String code);
    
    void create(TestingProductForm data);
    
    void modify(TestingProductForm data);
    
    boolean delete(String id);
    
    boolean setIsEnabled(TestingProductForm request);

    Pagination<AgencyProduct> getAgencyProducts(TestingProductSearcher searcher, int pageNo, int pageSize);

    AgencyProductDetails getAgencyProductDetails(String id);

    List<Product> getProduct();
}
