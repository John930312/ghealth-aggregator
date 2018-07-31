package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.AgencyProductOldPrice;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductOldPriceSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IAgencyProductOldPriceService extends PagerQueryHandler<AgencyProductOldPrice>
{
    void delete(AgencyProductOldPriceSearcher searchder);

    void create(AgencyProductOldPrice data);

    List<AgencyProductOldPrice> list(AgencyProductOldPriceSearcher searcher);
}
