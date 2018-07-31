package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AgencyProductOldPrice;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductOldPriceSearcher;

import java.util.List;

public interface AgencyProductOldPriceMapper
{
    int count(AgencyProductOldPriceSearcher searcher);

    List<AgencyProductOldPrice> search(AgencyProductOldPriceSearcher searcher);

    void insert(AgencyProductOldPrice data);

    void delete(AgencyProductOldPriceSearcher searchder);
}
