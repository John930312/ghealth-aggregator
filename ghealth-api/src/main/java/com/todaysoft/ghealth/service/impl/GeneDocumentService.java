package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.GeneDocumentMapper;
import com.todaysoft.ghealth.mybatis.model.GeneDocument;
import com.todaysoft.ghealth.mybatis.searcher.GeneDocumentSearcher;
import com.todaysoft.ghealth.service.IGeneDocumentService;

@Service
public class GeneDocumentService implements IGeneDocumentService
{
    @Autowired
    private GeneDocumentMapper mapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof GeneDocumentSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((GeneDocumentSearcher)searcher);
    }
    
    @Override
    public List<GeneDocument> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof GeneDocumentSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            GeneDocumentSearcher tis = (GeneDocumentSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((GeneDocumentSearcher)searcher);
    }
    
    @Override
    public List<GeneDocument> list(GeneDocumentSearcher searcher)
    {
        return mapper.search(searcher);
    }
    
    @Override
    @Transactional
    public void create(GeneDocument data)
    {
        mapper.insert(data);
    }
    
    @Override
    @Transactional
    public void modify(GeneDocument data)
    {
        mapper.update(data);
    }
    
    @Override
    @Transactional
    public void delete(GeneDocument data)
    {
        mapper.delete(data);
    }
    
}
