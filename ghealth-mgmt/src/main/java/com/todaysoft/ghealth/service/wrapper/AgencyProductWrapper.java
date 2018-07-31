package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.base.response.model.TestingItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.apa.AgencyProduct;

@Component
public class AgencyProductWrapper
{
    public List<AgencyProduct> wrap(List<com.todaysoft.ghealth.base.response.model.AgencyProduct> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        AgencyProduct agencyProduct;
        List<AgencyProduct> agencyProducts = new ArrayList<AgencyProduct>();
        
        for (com.todaysoft.ghealth.base.response.model.AgencyProduct record : records)
        {
            agencyProduct = new AgencyProduct();
            wrap(record, agencyProduct);
            agencyProducts.add(agencyProduct);
        }
        
        return agencyProducts;
    }

    private void wrap(com.todaysoft.ghealth.base.response.model.AgencyProduct source, AgencyProduct target)
    {
        BeanUtils.copyProperties(source, target, "productCreateTime", "productUpdateTime");
        target.setProductCreateTime(null == source.getProductCreateTime() ? null : new Date(source.getProductCreateTime()));
        target.setProductUpdateTime(null == source.getProductUpdateTime() ? null : new Date(source.getProductUpdateTime()));
    }

}
