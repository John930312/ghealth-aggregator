package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Gene;
import com.todaysoft.ghealth.mybatis.searcher.GeneSearcher;
import org.apache.ibatis.annotations.Param;

public interface GeneMapper
{
    int count(GeneSearcher searcher);
    
    List<Gene> search(GeneSearcher searcher);
    
    Gene get(String id);
    
    void insert(Gene gene);
    
    int update(Gene record);

    int countByName(String name);

    int countByIdName(@Param("id") String id, @Param("name")String name);

    int countBySymbol(String symbol);

    int countByIdSymbol(@Param("id") String id, @Param("symbol")String symbol);
}
