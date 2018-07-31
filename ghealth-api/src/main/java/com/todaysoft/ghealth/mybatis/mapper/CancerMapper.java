package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Cancer;
import com.todaysoft.ghealth.mybatis.searcher.CancerSearcher;
import org.apache.ibatis.annotations.Param;

public interface CancerMapper
{
    int count(CancerSearcher searcher);
    
    List<Cancer> search(CancerSearcher searcher);
    
    Cancer get(String id);
    
    int update(Cancer record);
    
    void insert(Cancer cancer);

    int countByName(String name);

    int countByIdName(@Param("id") String id, @Param("name")String name);
}
