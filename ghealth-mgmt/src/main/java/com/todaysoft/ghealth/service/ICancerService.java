package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.cancer.Cancer;
import com.todaysoft.ghealth.model.cancer.CancerForm;
import com.todaysoft.ghealth.model.cancer.CancerSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ICancerService
{
    Pagination<Cancer> searcher(CancerSearcher searcher, int pageNo, int pageSize);
    
    Cancer get(String id);
    
    void delete(String id);
    
    void create(CancerForm data);
    
    void modify(Cancer data);

    boolean isNameUnique(String id, String name);
    
}
