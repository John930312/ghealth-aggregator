package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.service.IItemLocusService;
import com.todaysoft.ghealth.service.IProductItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.TestingItem;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingItemRequest;
import com.todaysoft.ghealth.mgmt.request.QueryTestingItemsRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.TestingItemSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.ITestingItemService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.TestingItemWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.util.CollectionUtils;

@Component
public class TestingItemMgmtFacade
{
    @Autowired
    private TestingItemWrapper testingItemWrapper;
    
    @Autowired
    private ITestingItemService service;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IProductItemService productItemService;
    
    @Autowired
    private IItemLocusService itemLocusService;
    
    public PagerResponse<TestingItem> pager(QueryTestingItemsRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        TestingItemSearcher searcher = new TestingItemSearcher();
        searcher.setCode(request.getCode());
        searcher.setName(request.getName());
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.TestingItem> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.TestingItem>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.TestingItem> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<TestingItem> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), testingItemWrapper.wrap(pager.getRecords()));
        return new PagerResponse<TestingItem>(result);
    }
    
    public void create(MaintainTestingItemRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.TestingItem item = new com.todaysoft.ghealth.mybatis.model.TestingItem();
        item.setId(IdGen.uuid());
        item.setCode(request.getCode());
        item.setSexRestraint(request.getSexRestraint());
        item.setCategory(request.getCategory());
        item.setCategoryMapping(request.getCategoryMapping());
        item.setCreateTime(new Date());
        item.setCreatorName(account.getName());
        item.setDeleted(false);
        item.setEnabled(request.isEnabled());
        item.setName(request.getName());
        service.create(item);
    }
    
    public TestingItem getDetails(QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        TestingItem item = new TestingItem();
        com.todaysoft.ghealth.mybatis.model.TestingItem itemPojo = service.get(request.getId());
        
        testingItemWrapper.wrap(itemPojo, item);
        return item;
    }
    
    public void modify(MaintainTestingItemRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        com.todaysoft.ghealth.mybatis.model.TestingItem item = service.get(request.getId());
        item.setCode(request.getCode());
        item.setCategory(request.getCategory());
        item.setCategoryMapping(request.getCategoryMapping());
        item.setDeleted(false);
        item.setEnabled(request.isEnabled());
        item.setName(request.getName());
        item.setSexRestraint(request.getSexRestraint());
        item.setUpdatorName(account.getName());
        item.setUpdateTime(new Date());
        service.modify(item);
    }
    
    public ObjectResponse<Boolean> delete(MaintainTestingItemRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        List<String> productIds = productItemService.getProductIdByItemId(request.getId());
        if (CollectionUtils.isEmpty(productIds))
        {
            com.todaysoft.ghealth.mybatis.model.TestingItem item = service.get(request.getId());
            item.setDeleted(true);
            item.setDeleteTime(new Date());
            item.setDeletorName(account.getName());
            service.modify(item);
            itemLocusService.deleteByItemId(request.getId());
            return new ObjectResponse<>(true);
        }
        else
        {
            return new ObjectResponse<>(false);
        }
        
    }
    
    public ListResponse<TestingItem> list(QueryTestingItemsRequest request)
    {
        TestingItemSearcher searcher = new TestingItemSearcher();
        searcher.setCode(request.getCode());
        searcher.setName(request.getName());
        searcher.setEnabled(1);
        return new ListResponse<TestingItem>(testingItemWrapper.wrap(service.query(searcher, 10, -1)));
    }
    
    public ObjectResponse<Boolean> isCodeUnique(MaintainTestingItemRequest request)
    {
        boolean unique = service.isCodeUnique(request.getId(), request.getCode());
        return new ObjectResponse<Boolean>(unique);
    }
    
    public ObjectResponse<Boolean> setIsEnabled(MaintainTestingItemRequest request)
    {
        boolean enabled = Boolean.valueOf(request.isEnabled());
        com.todaysoft.ghealth.mybatis.model.TestingItem testingItem = service.get(request.getId());
        //设置  禁用
        if (!enabled)
        {
            List<String> productIds = productItemService.getProductIdByItemId(request.getId());
            if (!CollectionUtils.isEmpty(productIds))
            {
                return new ObjectResponse<>(false);
            }
        }
        testingItem.setEnabled(enabled);
        service.modify(testingItem);
        return new ObjectResponse<>(true);
    }
    
}
