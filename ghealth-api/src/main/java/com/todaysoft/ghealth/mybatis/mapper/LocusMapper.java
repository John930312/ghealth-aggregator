package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.todaysoft.ghealth.mybatis.model.Locus;
import com.todaysoft.ghealth.mybatis.searcher.LocusSearcher;

public interface LocusMapper
{
    List<Locus> search(LocusSearcher searcher);
    
    int count(LocusSearcher searcher);
    
    List<Locus> getLocusByNames(@Param("names") Set<String> names);
    
    Locus get(String id);
    
    void create(Locus data);
    
    void modify(Locus record);
}