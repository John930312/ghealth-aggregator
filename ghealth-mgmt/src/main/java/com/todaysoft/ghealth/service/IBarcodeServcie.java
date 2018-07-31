package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.barcode.Barcode;
import com.todaysoft.ghealth.model.barcode.BarcodeSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface IBarcodeServcie
{
    Pagination<Barcode> searcher(BarcodeSearcher searcher, int pageNo, int pageSize);
    
    Integer add(Barcode data);
}
