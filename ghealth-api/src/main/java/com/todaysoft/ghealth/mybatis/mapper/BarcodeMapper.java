package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.Barcode;
import com.todaysoft.ghealth.mybatis.searcher.BarcodeSearcher;

import java.util.Date;
import java.util.List;

public interface BarcodeMapper
{
    int insert(Barcode record);
    
    Barcode get(Integer id);
    
    int count(BarcodeSearcher searcher);
    
    List<Barcode> search(BarcodeSearcher searcher);

    Integer getMaxCodeByPrefixCode(BarcodeSearcher searcher);

    Date getEarliestCode();
}