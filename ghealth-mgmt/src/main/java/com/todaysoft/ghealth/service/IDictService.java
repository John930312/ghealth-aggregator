package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.dict.Dict;
import com.todaysoft.ghealth.model.dict.DictSearcher;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface IDictService
{
    Pagination<Dict> searcher(DictSearcher searcher, int pageNo, int pageSize);
    
    Dict get(String id);
    
    void modify(Dict data);
    
    void change(Dict data);
    
    List<Dict> getDictsByCategory(String category);
    
    Dict getDictByCategoryAndValue(String category,String dictValue);
}
