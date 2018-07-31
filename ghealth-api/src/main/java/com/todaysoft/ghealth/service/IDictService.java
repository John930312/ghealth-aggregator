package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Dict;
import com.todaysoft.ghealth.mybatis.model.DictForm;
import com.todaysoft.ghealth.mybatis.searcher.DictSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IDictService extends PagerQueryHandler<Dict>
{
    List<Dict> findList(DictSearcher searcher);
    
    Dict findByCategoryAndValue(DictSearcher searcher);
    
    Dict get(String id);
    
    String getText(String category, String value);
    
    void create(DictForm data);
    
    void deleteByCategory(String category);
    
    void modify(DictForm data);
}
