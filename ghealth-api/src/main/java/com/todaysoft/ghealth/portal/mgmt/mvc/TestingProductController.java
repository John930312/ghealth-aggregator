package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.portal.agcy.facade.AgencyProductAgcyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Product;
import com.todaysoft.ghealth.base.response.model.ProductDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingProductRequest;
import com.todaysoft.ghealth.mgmt.request.QueryTestingProductsRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.TestingProductMgmtFacade;

@RestController
@RequestMapping("/mgmt/testing-products")
public class TestingProductController
{
    @Autowired
    private TestingProductMgmtFacade facade;

    @Autowired
    private AgencyProductAgcyFacade agencyProductAgcyFacade;
    
    @RequestMapping("/pager")
    public PagerResponse<Product> pager(@RequestBody QueryTestingProductsRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping(value = "/details")
    public ObjectResponse<ProductDetails> getDetails(@RequestBody QueryDetailsRequest request)
    {
        return new ObjectResponse<ProductDetails>(facade.get(request));
    }
    
    @RequestMapping("/unique/code")
    public ObjectResponse<Boolean> isCodeUnique(@RequestBody MaintainTestingProductRequest request)
    {
        return facade.isCodeUnique(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainTestingProductRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainTestingProductRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/delete")
    public ObjectResponse<Boolean> delete(@RequestBody MaintainTestingProductRequest request)
    {
        return facade.delete(request);
    }
    
    @RequestMapping("/setIsEnabled")
    public ObjectResponse<Boolean> setIsEnabled(@RequestBody MaintainTestingProductRequest request)
    {
        return facade.setIsEnabled(request);
    }
    
    @RequestMapping("/getAgencyProducts")
    public PagerResponse<AgencyProduct> getAgencyProducts(@RequestBody QueryTestingProductsRequest request)
    {
       return facade.getAgencyProducts(request);
    }

    @RequestMapping("/getAgencyProductDetails")
    public ObjectResponse<AgencyProductDetails> getAgencyProductDetails(@RequestBody QueryDetailsRequest request)
    {
        return agencyProductAgcyFacade.details(request);
    }

    @RequestMapping("/getProduct")
    public ListResponse<Product> getProduct(@RequestBody QueryTestingProductsRequest request)
    {
        return facade.getProduct(request);
    }
    
}
