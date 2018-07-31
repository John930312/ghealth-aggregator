package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.*;
import com.todaysoft.ghealth.service.wrapper.AgencyProductWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Product;
import com.todaysoft.ghealth.base.response.model.ProductDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingProductRequest;
import com.todaysoft.ghealth.mgmt.request.QueryTestingProductsRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.mybatis.searcher.ProductSearcher;
import com.todaysoft.ghealth.mybatis.searcher.TestingItemSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.ProductWrapper;
import com.todaysoft.ghealth.service.wrapper.TestingItemWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class TestingProductMgmtFacade
{
    @Autowired
    private ITestingProductService service;
    
    @Autowired
    private ITestingItemService testingItemService;
    
    @Autowired
    private IProductItemService productItemService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IAgencyProductService agencyProductService;
    
    @Autowired
    private ProductWrapper wrapper;
    
    @Autowired
    private TestingItemWrapper testingItemWrapper;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private AgencyProductWrapper agencyProductWrapper;
    
    public PagerResponse<Product> pager(QueryTestingProductsRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        ProductSearcher searcher = new ProductSearcher();
        searcher.setCode(request.getCode());
        searcher.setName(request.getName());
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Product> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Product>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Product> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Product> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<Product>(result);
    }
    
    public void create(MaintainTestingProductRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Product product = new com.todaysoft.ghealth.mybatis.model.Product();
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        product.setId(IdGen.uuid());
        product.setCode(request.getCode());
        product.setGuidingPrice(request.getGuidingPrice());
        product.setCreateTime(new Date());
        product.setCreatorName(account.getName());
        product.setDeleted(false);
        product.setEnabled(request.isEnabled());
        product.setName(request.getName());
        product.setSexRestraint(request.getSexRestraint());
        
        List<String> items = new ArrayList<String>();
        
        if (!CollectionUtils.isEmpty(request.getTestingItems()))
        {
            items.addAll(request.getTestingItems());
        }
        
        service.create(product, items);
    }
    
    public ObjectResponse<Boolean> delete(MaintainTestingProductRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        List<Order> orders = getOrders(request);
        if (CollectionUtils.isEmpty(orders))
        {
            com.todaysoft.ghealth.mybatis.model.Product product = service.get(request.getId());
            product.setDeleted(true);
            product.setDeleteTime(new Date());
            product.setDeletorName(account.getName());
            service.modify(product, new ArrayList<>());
            productItemService.deleteItemsByProductId(request.getId());
            return new ObjectResponse<>(true);
        }
        return new ObjectResponse<>(false);
    }
    
    public ProductDetails get(QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Product product = service.get(request.getId());
        
        ProductDetails details = wrapper.wrap(product);
        
        if (null == details)
        {
            return null;
        }
        
        TestingItemSearcher searcher = new TestingItemSearcher();
        searcher.setProductId(details.getId());
        List<TestingItem> testingItems = testingItemService.query(searcher, 0, -1);
        details.setTestingItems(testingItemWrapper.wrap(testingItems));
        return details;
    }
    
    public void modify(MaintainTestingProductRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        com.todaysoft.ghealth.mybatis.model.Product product = service.get(request.getId());
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        productItemService.deleteItemsByProductId(product.getId());
        product.setCode(request.getCode());
        product.setEnabled(request.isEnabled());
        product.setName(request.getName());
        product.setSexRestraint(request.getSexRestraint());
        product.setGuidingPrice(request.getGuidingPrice());
        product.setDeleted(false);
        List<String> items = new ArrayList<String>();
        
        if (!CollectionUtils.isEmpty(request.getTestingItems()))
        {
            items.addAll(request.getTestingItems());
        }
        product.setUpdateTime(new Date());
        product.setUpdatorName(account.getName());
        service.modify(product, items);
    }
    
    public ObjectResponse<Boolean> isCodeUnique(MaintainTestingProductRequest request)
    {
        boolean unique = service.isCodeUnique(request.getId(), request.getCode());
        return new ObjectResponse<Boolean>(unique);
    }
    
    public ObjectResponse<Boolean> setIsEnabled(MaintainTestingProductRequest request)
    {
        boolean enabled = Boolean.valueOf(request.isEnabled());
        com.todaysoft.ghealth.mybatis.model.Product product = service.get(request.getId());
        
        if (!enabled)
        {
            List<Order> orders = getOrders(request);
            if (!CollectionUtils.isEmpty(orders))
            {
                //判断  订单状态
                return new ObjectResponse<>(false);
            }
        }
        product.setEnabled(enabled);
        service.modify(product, new ArrayList<>());
        return new ObjectResponse<>(true);
        
    }
    
    private List<Order> getOrders(MaintainTestingProductRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        searcher.setProductId(request.getId());
        return orderService.list(searcher);
    }
    
    public PagerResponse<AgencyProduct> getAgencyProducts(QueryTestingProductsRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        AgencyProductSearcher searcher = new AgencyProductSearcher();
        searcher.setAgencyId(request.getAgencyId());
        searcher.setProductCode(request.getCode());
        searcher.setProductName(request.getName());
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyProduct> queryer =
            new PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyProduct>(agencyProductService);
        Pager<com.todaysoft.ghealth.mybatis.model.AgencyProduct> pager = queryer.query(searcher, pageNo, pageSize);
        
        Pager<AgencyProduct> result =
            Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), agencyProductWrapper.wrap(pager.getRecords()));
        return new PagerResponse<AgencyProduct>(result);
    }
    
    public ObjectResponse<AgencyProductDetails> getAgencyProductDetails(QueryTestingProductsRequest request)
    {
        AgencyProductSearcher searcher = new AgencyProductSearcher();
        searcher.setAgencyId(request.getAgencyId());
        searcher.setProductName(request.getName());
        List<com.todaysoft.ghealth.mybatis.model.AgencyProduct> list = agencyProductService.list(searcher);
        AgencyProductDetails agencyProductDetails = null;
        if (!CollectionUtils.isEmpty(list))
        {
            agencyProductDetails = agencyProductWrapper.wrapAgencyProductDateails(list.get(0));
        }
        return new ObjectResponse<>(agencyProductDetails);
    }


    public ListResponse<Product> getProduct(QueryTestingProductsRequest request)
    {
        ProductSearcher searcher = new ProductSearcher();
        return new ListResponse<>(wrapper.wrap(service.list(searcher)));
    }
}
