package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.QueryAgencyProductsRequest;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.model.product.AgencyProduct;
import com.todaysoft.ghealth.model.product.AgencyProductDetails;
import com.todaysoft.ghealth.model.product.ProductSearcher;
import com.todaysoft.ghealth.model.product.TestingItem;
import com.todaysoft.ghealth.service.IAgencyProductService;
import com.todaysoft.ghealth.service.wrapper.AgencyProductWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class AgencyProductService implements IAgencyProductService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private AgencyProductWrapper agencyProductWrapper;
    
    @Override
    public Pagination<AgencyProduct> searcher(ProductSearcher searcher, int pageNo, int pageSize)
    {
        QueryAgencyProductsRequest request = new QueryAgencyProductsRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct> response = gateway.request("/agcy/agent-products/pager",
            request,
            new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.AgencyProduct> pager = response.getData();
        
        Pagination<AgencyProduct> pagination = new Pagination<AgencyProduct>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        
        pagination.setRecords(agencyProductWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public AgencyProductDetails getAgencyProductDetails(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyProductDetails> response = gateway.request("/agcy/agent-products/details",
            request,
            new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyProductDetails>>()
            {
            });
        
        com.todaysoft.ghealth.base.response.model.AgencyProductDetails data = response.getData();
        
        if (null == data)
        {
            return null;
        }
        
        AgencyProductDetails details = new AgencyProductDetails();
        BeanUtils.copyProperties(data, details, "productCreateTime", "productUpdateTime", "testingItems");
        details.setProductCreateTime(null == data.getProductCreateTime() ? null : new Date(data.getProductCreateTime()));
        details.setProductUpdateTime(null == data.getProductUpdateTime() ? null : new Date(data.getProductUpdateTime()));
        
        if (CollectionUtils.isEmpty(data.getTestingItems()))
        {
            details.setTestingItems(Collections.emptyList());
        }
        else
        {
            TestingItem testingItem;
            List<TestingItem> testingItems = new ArrayList<TestingItem>();
            
            for (com.todaysoft.ghealth.base.response.model.TestingItem record : data.getTestingItems())
            {
                testingItem = new TestingItem();
                BeanUtils.copyProperties(record, testingItem);
                testingItems.add(testingItem);
            }
            details.setInGracePeriod(isEffectiveDate(details));
            details.setTestingItems(testingItems);
        }
        
        return details;
    }
    
    @Override
    public List<AgencyProduct> list(ProductSearcher searcher)
    {
        QueryAgencyProductsRequest request = new QueryAgencyProductsRequest();
        BeanUtils.copyProperties(searcher, request);
        ListResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct> response = gateway.request("/agcy/agent-products/list",
            request,
            new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct>>()
            {
            });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        return agencyProductWrapper.wrap(response.getData());
    }
    
    @Override
    public List<Map> getSimpleAgencyProducts(List<AgencyProduct> agencyProducts)
    {
        List<Map> datas = new ArrayList<Map>();
        if (CollectionUtils.isEmpty(agencyProducts))
        {
            return datas;
        }
        agencyProducts.forEach(agencyProduct -> {
            Map<String, String> map = new HashMap<String, String>();
            if (agencyProduct.getProductSexRestraint().equals("0") || agencyProduct.getProductSexRestraint().equals("1"))
            {
                map.put("title", agencyProduct.getProductName());
                map.put("value", agencyProduct.getProductId());
                datas.add(map);
            }
            
        });
        return datas;
    }
    
    @Override
    public List<Map> getSimAgencyProducts(List<AgencyProduct> agencyProducts)
    {
        List<Map> datas = new ArrayList<Map>();
        if (CollectionUtils.isEmpty(agencyProducts))
        {
            return datas;
        }
        agencyProducts.forEach(agencyProduct -> {
            Map<String, String> map = new HashMap<String, String>();
            if (agencyProduct.getProductSexRestraint().equals("0") || agencyProduct.getProductSexRestraint().equals("2"))
            {
                map.put("title", agencyProduct.getProductName());
                map.put("value", agencyProduct.getProductId());
                datas.add(map);
            }
            
        });
        return datas;
    }
    
    @Override
    public boolean isUniqueCode(String code)
    {
        QueryAgencyProductsRequest request = new QueryAgencyProductsRequest();
        request.setCode(code.trim());
        ObjectResponse<Boolean> result = gateway.request("/agcy/agent-products/isUniqueCode", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return result.getData();
    }
    
    public static boolean isEffectiveDate(AgencyProductDetails details)
    {
        Long startTime = details.getStartTime();
        Long endTime = details.getEndTime();
        
        if (!details.isDiscount())
        {
            return false;
        }
        LocalDate localDate = LocalDate.MAX;
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        
        endTime = Optional.ofNullable(endTime).orElse(instant.getEpochSecond());
        if (Objects.isNull(startTime) || Objects.isNull(endTime))
        {
            return false;
        }
        if (System.currentTimeMillis() == startTime || System.currentTimeMillis() == endTime)
        {
            return true;
        }
        
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        
        Calendar begin = Calendar.getInstance();
        begin.setTime(new Date(startTime));
        
        Calendar end = Calendar.getInstance();
        end.setTime(new Date(endTime));
        
        if (date.after(begin) && date.before(end))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
}
