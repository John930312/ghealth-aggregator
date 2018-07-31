package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Gene;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IGeneService extends PagerQueryHandler<Gene>
{
    
    Gene get(String id);
    
    void create(Gene data);
    
    void modify(Gene data);

    boolean isNameUnique(String id, String name);

    boolean isSymbolUnique(String id, String symbol);
    
}
