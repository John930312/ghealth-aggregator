package com.todaysoft.ghealth.service;

import java.util.List;
import java.util.Set;

import com.todaysoft.ghealth.mybatis.model.Locus;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface ILocusService extends PagerQueryHandler<Locus>
{
    List<Locus> list(Set<String> names);
    
    Locus get(String id);
    
    void create(Locus data);
    
    void modify(Locus data);
    
    boolean isNameUnique(String id, String name);
}
