package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.Dict;
import com.todaysoft.ghealth.mybatis.model.DictForm;
import com.todaysoft.ghealth.mybatis.searcher.DictSearcher;

import java.util.List;

public interface DictMapper
{
    int count(DictSearcher searcher);
    
    List<Dict> search(DictSearcher searcher);
    
    List<Dict> findList(DictSearcher searcher);
    
    Dict findByCategoryAndValue(DictSearcher searcher);
    
    Dict get(String id);
    
    void create(DictForm data);
    
    void update(DictForm data);
    
    void deleteByCategory(String category);
    
}
