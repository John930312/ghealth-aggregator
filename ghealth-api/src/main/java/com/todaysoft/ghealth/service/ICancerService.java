package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Cancer;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface ICancerService extends PagerQueryHandler<Cancer>
{
    
    Cancer get(String id);
    
    void modify(Cancer data);
    
    void create(Cancer data);

    boolean isNameUnique(String id, String name);
}
