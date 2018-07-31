package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.model.product.AgencyProductOldPrice;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class AgencyProductOldPriceWrapper
{

    public List<AgencyProductOldPrice> wrap(List<com.todaysoft.ghealth.base.response.model.AgencyProductOldPrice> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        AgencyProductOldPrice agencyProductOldPrice;
        List<AgencyProductOldPrice> agencyProductOldPrices = new ArrayList<AgencyProductOldPrice>();
        for (com.todaysoft.ghealth.base.response.model.AgencyProductOldPrice record : records)
        {
            agencyProductOldPrice = new AgencyProductOldPrice();
            wrap(record,agencyProductOldPrice);
            agencyProductOldPrices.add(agencyProductOldPrice);
        }
        return agencyProductOldPrices;
    }

    private void wrap(com.todaysoft.ghealth.base.response.model.AgencyProductOldPrice source, AgencyProductOldPrice target)
    {
        BeanUtils.copyProperties(source,target,"operateTime");
        target.setOperateTime(null == source.getOperateTime() ? null : new Date(source.getOperateTime()));
    }
}
