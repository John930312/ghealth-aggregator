package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.GeneDetails;
import com.todaysoft.ghealth.model.gene.Gene;
import com.todaysoft.ghealth.model.gene.GeneForm;
import com.todaysoft.ghealth.model.gene.GeneSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface IGeneService
{
    Pagination<Gene> searcher(GeneSearcher searcher, int pageNo, int pageSize);
    
    GeneDetails get(String id);
    
    void create(GeneForm data);
    
    void modify(GeneForm data);
    
    void delete(String id);

    boolean isNameUnique(String id, String name);

    boolean isSymbolUnique(String id, String symbol);
}
