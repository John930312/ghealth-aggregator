package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainBarcodeRequest;
import com.todaysoft.ghealth.mgmt.request.QueryBarcodeRequest;
import com.todaysoft.ghealth.model.barcode.Barcode;
import com.todaysoft.ghealth.model.barcode.BarcodeSearcher;
import com.todaysoft.ghealth.service.IBarcodeServcie;
import com.todaysoft.ghealth.service.wrapper.BarcodeWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class BarcodeService implements IBarcodeServcie
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private BarcodeWrapper barcodeWrapper;
    
    @Override
    public Pagination<Barcode> searcher(BarcodeSearcher searcher, int pageNo, int pageSize)
    {
        QueryBarcodeRequest request = new QueryBarcodeRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Barcode> pagerResponse =
            gateway.request("/mgmt/barcode/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Barcode>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Barcode> pager = pagerResponse.getData();
        Pagination<Barcode> pagination = new Pagination<Barcode>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(barcodeWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public Integer add(Barcode data)
    {
        MaintainBarcodeRequest request = new MaintainBarcodeRequest();
        BeanUtils.copyProperties(data, request);
        return gateway.request("/mgmt/barcode/create", request, Integer.class);
    }
}
