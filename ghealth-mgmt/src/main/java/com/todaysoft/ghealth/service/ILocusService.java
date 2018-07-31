package com.todaysoft.ghealth.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.ghealth.model.locus.Locus;
import com.todaysoft.ghealth.model.locus.LocusSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ILocusService
{
    Pagination<Locus> pagination(LocusSearcher searcher, int pageNo, int pageSize);
    
    Map<String, Locus> getLocusAsNameMappings(List<String> names);
    
    Locus get(String id);
    
    void create(Locus data);
    
    void modify(Locus data);
    
    void delete(String id);
    
    boolean isNameUnique(String id, String name);
}
