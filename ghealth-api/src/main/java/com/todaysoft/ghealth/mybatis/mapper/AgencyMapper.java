package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.searcher.AgencySearcher;

public interface AgencyMapper
{
    int count(AgencySearcher searcher);
    
    List<Agency> search(AgencySearcher searcher);
    
    Agency get(String id);
    
    void insert(Agency data);
    
    void update(Agency data);

    Agency getReportData(String id);

    List<String> getSubAccount(String id);
}
