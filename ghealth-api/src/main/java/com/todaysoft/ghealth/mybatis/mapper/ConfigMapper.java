package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.mybatis.searcher.ConfigSearcher;

public interface ConfigMapper
{
    int delete(String id);
    
    int insert(Config record);
    
    Config get(String id);
    
    int modify(Config record);
    
    int count(ConfigSearcher searcher);
    
    List<Config> search(ConfigSearcher searcher);
    
}