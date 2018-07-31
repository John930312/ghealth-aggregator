package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.GeneDocument;
import com.todaysoft.ghealth.mybatis.searcher.GeneDocumentSearcher;

public interface GeneDocumentMapper
{
    int count(GeneDocumentSearcher searcher);
    
    List<GeneDocument> search(GeneDocumentSearcher searcher);
    
    void insert(GeneDocument geneDocument);
    
    int update(GeneDocument record);
    
    void delete(GeneDocument geneDocument);
}
