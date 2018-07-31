package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.AgencyProduct;
import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IAgencyProductService extends PagerQueryHandler<AgencyProduct>
{
    AgencyProduct get(String id);
    
    void allocate(AgencyProduct data);
    
    void delete(AgencyProductSearcher searchder);
    
    List<AgencyProduct> list(AgencyProductSearcher searchder);

    void modify(AgencyProduct data);

    String getIdByCode(String code,String agencyId);

    AgencyProduct getBySearch(AgencyProductSearcher searchder);


}
