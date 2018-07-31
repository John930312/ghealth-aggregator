package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Barcode;
import com.todaysoft.ghealth.mybatis.searcher.BarcodeSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.Date;
import java.util.List;

public interface IBaecodeService extends PagerQueryHandler<Barcode>
{
    @Override
    List<Barcode> query(Object searcher, int offset, int limit);
    
    @Override
    int count(Object searcher);
    
    void create(Barcode data);
    
    Integer getMaxCodeByPrefixCode(BarcodeSearcher searcher);

    Date getEarliestCode();
}
