package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.AgencyProduct;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;
import org.apache.ibatis.annotations.Param;

public interface AgencyProductMapper
{
    int count(AgencyProductSearcher searcher);
    
    List<AgencyProduct> search(AgencyProductSearcher searcher);
    
    AgencyProduct get(String id);
    
    void create(AgencyProduct data);
    
    void delete(AgencyProductSearcher searchder);

    void modify(AgencyProduct data);

    String getIdByCode(@Param("code")String code, @Param("agencyId")String agencyId);

    AgencyProduct getBySearch(AgencyProductSearcher searchder);

    
}
