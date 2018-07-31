package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.model.apa.AgencyProduct;
import com.todaysoft.ghealth.service.wrapper.AgencyProductWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingProductRequest;
import com.todaysoft.ghealth.mgmt.request.QueryTestingProductsRequest;
import com.todaysoft.ghealth.model.product.Product;
import com.todaysoft.ghealth.model.product.ProductDetails;
import com.todaysoft.ghealth.model.product.TestingProductForm;
import com.todaysoft.ghealth.model.product.TestingProductSearcher;
import com.todaysoft.ghealth.service.ITestingProductService;
import com.todaysoft.ghealth.service.wrapper.ProductWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class TestingProductService implements ITestingProductService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private ProductWrapper productWrapper;
    
    @Autowired
    private AgencyProductWrapper agencyProductWrapper;
    
    @Override
    public Pagination<Product> search(TestingProductSearcher searcher, int pageNo, int pageSize)
    {
        QueryTestingProductsRequest request = new QueryTestingProductsRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        PagerResponse<com.todaysoft.ghealth.base.response.model.Product> response = gateway
            .request("/mgmt/testing-products/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Product>>()
            {
            });
        
        Pager<com.todaysoft.ghealth.base.response.model.Product> pager = response.getData();
        Pagination<Product> pagination = new Pagination<Product>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(productWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public ProductDetails get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.ProductDetails> response = gateway.request("/mgmt/testing-products/details",
            request,
            new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.ProductDetails>>()
            {
            });
        return productWrapper.wrap(response.getData());
    }
    
    @Override
    public boolean isCodeUnique(String id, String code)
    {
        MaintainTestingProductRequest request = new MaintainTestingProductRequest();
        request.setId(id);
        request.setCode(code);
        
        ObjectResponse<Boolean> response =
            gateway.request("/mgmt/testing-products/unique/code", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
            {
            });
        
        if (null == response.getData())
        {
            return false;
        }
        
        return response.getData().booleanValue();
    }
    
    @Override
    public void create(TestingProductForm data)
    {
        MaintainTestingProductRequest request = new MaintainTestingProductRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/testing-products/create", request);
    }
    
    @Override
    public boolean delete(String id)
    {
        MaintainTestingProductRequest request = new MaintainTestingProductRequest();
        request.setId(id);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/testing-products/delete", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public boolean setIsEnabled(TestingProductForm data)
    {
        MaintainTestingProductRequest request = new MaintainTestingProductRequest();
        BeanUtils.copyProperties(data, request);
        ObjectResponse<Boolean> response =
            gateway.request("/mgmt/testing-products/setIsEnabled", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
            {
            });
        return response.getData();
    }
    
    @Override
    public Pagination<AgencyProduct> getAgencyProducts(TestingProductSearcher searcher, int pageNo, int pageSize)
    {
        QueryTestingProductsRequest request = new QueryTestingProductsRequest();
        BeanUtils.copyProperties(searcher, request);
        PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct> result = gateway.request("/mgmt/testing-products/getAgencyProducts",
            request,
            new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.AgencyProduct> pager = result.getData();
        Pagination<AgencyProduct> pagination = new Pagination<AgencyProduct>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(agencyProductWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public AgencyProductDetails getAgencyProductDetails(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<AgencyProductDetails> result =
            gateway.request("/mgmt/testing-products/getAgencyProductDetails", request, new ParameterizedTypeReference<ObjectResponse<AgencyProductDetails>>()
            {
            });
        
        return result.getData();
    }
    
    @Override
    public void modify(TestingProductForm data)
    {
        MaintainTestingProductRequest request = new MaintainTestingProductRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/testing-products/modify", request);
    }


    @Override
    public List<Product> getProduct()
    {
        QueryTestingProductsRequest request = new QueryTestingProductsRequest();
        ListResponse<com.todaysoft.ghealth.base.response.model.Product> response = gateway.request("/mgmt/testing-products/getProduct",
                request,
                new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Product>>()
                {
                });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        return productWrapper.wrap(response.getData());
    }

}
