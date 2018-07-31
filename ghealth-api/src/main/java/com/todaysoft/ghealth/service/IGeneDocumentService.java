package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.GeneDocument;
import com.todaysoft.ghealth.mybatis.searcher.GeneDocumentSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IGeneDocumentService extends PagerQueryHandler<GeneDocument>
{
    List<GeneDocument> list(GeneDocumentSearcher searcher);
    
    void create(GeneDocument data);
    
    void modify(GeneDocument data);
    
    void delete(GeneDocument data);
}
