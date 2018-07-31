package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.BarcodeMapper;
import com.todaysoft.ghealth.mybatis.model.Barcode;
import com.todaysoft.ghealth.mybatis.searcher.BarcodeSearcher;
import com.todaysoft.ghealth.service.IBaecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BarcodeService implements IBaecodeService
{
    @Autowired
    private BarcodeMapper mapper;
    
    @Override
    public List<Barcode> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof BarcodeSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            BarcodeSearcher tis = (BarcodeSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((BarcodeSearcher)searcher);
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof BarcodeSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((BarcodeSearcher)searcher);
    }
    
    @Override
    public void create(Barcode data)
    {
        mapper.insert(data);
    }

    @Override
    public Integer getMaxCodeByPrefixCode(BarcodeSearcher searcher)
    {
       return mapper.getMaxCodeByPrefixCode(searcher);
    }

    @Override
    public Date getEarliestCode() {
        return mapper.getEarliestCode();
    }
}
