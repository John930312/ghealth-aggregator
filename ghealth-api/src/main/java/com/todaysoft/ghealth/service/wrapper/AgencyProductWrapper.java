package com.todaysoft.ghealth.service.wrapper;

import java.util.*;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.mybatis.searcher.TestingItemSearcher;
import com.todaysoft.ghealth.service.ITestingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.Product;

@Component
public class AgencyProductWrapper
{

    @Autowired
    private ITestingItemService testingItemService;

    @Autowired
    private TestingItemWrapper testingItemWrapper;

    public List<AgencyProduct> wrap(List<com.todaysoft.ghealth.mybatis.model.AgencyProduct> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        AgencyProduct agencyProduct;
        List<AgencyProduct> agencyProducts = new ArrayList<AgencyProduct>();
        
        for (com.todaysoft.ghealth.mybatis.model.AgencyProduct record : records)
        {
            agencyProduct = new AgencyProduct();
            wrap(record, agencyProduct);
            agencyProducts.add(agencyProduct);
        }
        
        return agencyProducts;
    }


    public AgencyProductDetails wrap(com.todaysoft.ghealth.mybatis.model.AgencyProduct record)
    {
        AgencyProductDetails details = new AgencyProductDetails();
        wrap(record, details);
        return details;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.AgencyProduct source, AgencyProduct target)
    {
        target.setId(source.getId());
        
        if (null != source.getAgency())
        {
            Agency agency = source.getAgency();
            target.setAgencyId(agency.getId());
            target.setAgencyCode(agency.getCode());
            target.setAgencyAbbr(agency.getAbbr());
            target.setAgencyName(agency.getName());
            target.setAgencyAccountAmount(agency.getAccountAmount());
            target.setAgencyAuthorizationAmount(agency.getAuthorizationAmount());
        }
        
        if (null != source.getProduct())
        {
            Product product = source.getProduct();
            target.setProductId(product.getId());
            target.setProductCode(product.getCode());
            target.setProductName(product.getName());
            target.setGuidingPrice(product.getGuidingPrice());
            target.setProductEnabled(product.isEnabled());
            target.setProductSexRestraint(product.getSexRestraint());
            //数据字典
            target.setProductCreateTime(null == product.getCreateTime() ? null : product.getCreateTime().getTime());
            target.setProductCreatorName(product.getCreatorName());
            target.setProductUpdateTime(null == product.getUpdateTime() ? null : product.getUpdateTime().getTime());
            target.setProductUpdatorName(product.getUpdatorName());
        }
        
        target.setAgencyPrice(source.getAgencyPrice());
        target.setDiscountPrice(source.getDiscountPrice());
        target.setDiscount(source.isDiscount());
        target.setStartTime(null == source.getStartTime() ? null : source.getStartTime().getTime());
        target.setEndTime(null == source.getEndTime() ? null : source.getEndTime().getTime());
    }

    public AgencyProductDetails wrapAgencyProductDateails(com.todaysoft.ghealth.mybatis.model.AgencyProduct agencyProduct)
    {
        TestingItemSearcher searcher = new TestingItemSearcher();
        searcher.setProductId(agencyProduct.getProduct().getId());
        List<com.todaysoft.ghealth.mybatis.model.TestingItem> items = testingItemService.query(searcher, 0, -1);

        AgencyProductDetails details = wrap(agencyProduct);

        if (CollectionUtils.isEmpty(items))
        {
            details.setTestingItems(Collections.emptyList());
        }
        else
        {
            details.setTestingItems(testingItemWrapper.wrap(items));
        }
        details.setInGracePeriod(isEffectiveDate(details.getStartTime(), details.getEndTime()));

        return details;
    }

    public static boolean isEffectiveDate(Long startTime, Long endTime)
    {
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
