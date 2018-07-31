package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Barcode;
import com.todaysoft.ghealth.mgmt.request.MaintainBarcodeRequest;
import com.todaysoft.ghealth.mgmt.request.QueryBarcodeRequest;
import com.todaysoft.ghealth.mybatis.searcher.BarcodeSearcher;
import com.todaysoft.ghealth.service.IBaecodeService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.BarcodeWrapper;
import com.todaysoft.ghealth.utils.DateOperateUtils;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
public class BarcodeFacade
{
    @Autowired
    private IBaecodeService service;
    
    @Autowired
    private BarcodeWrapper wrap;
    
    public PagerResponse<Barcode> pager(QueryBarcodeRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        BarcodeSearcher searcher = new BarcodeSearcher();
        BeanUtils.copyProperties(request, searcher);
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Barcode> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Barcode>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Barcode> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Barcode> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrap.wrap(pager.getRecords()));
        return new PagerResponse<Barcode>(result);
    }
    
    @Transactional
    public Integer create(MaintainBarcodeRequest request)
    {
        BarcodeSearcher searcher = new BarcodeSearcher();
        BeanUtils.copyProperties(request, searcher);
        Integer maxCode = Optional.ofNullable(service.getMaxCodeByPrefixCode(searcher)).orElse(0);
        Date now = new Date();
        Date earliestDate = Objects.isNull(service.getEarliestCode()) ? now : service.getEarliestCode();
        Date date = earliestDate.after(now) ? earliestDate : now;
        
        for (int i = 1; i <= request.getCount(); i++)
        {
            String barCodeComplete = request.getPrefixCode() + request.getIsFree() + formatNumber(String.valueOf(maxCode + i));
            com.todaysoft.ghealth.mybatis.model.Barcode data = new com.todaysoft.ghealth.mybatis.model.Barcode();
            BeanUtils.copyProperties(request, data);
            data.setBarCodeComplete(barCodeComplete);
            data.setId(IdGen.uuid());
            data.setCode(maxCode + i);
            data.setIsDelete(0);
            date = DateOperateUtils.addOneSecond(date, 1);
            data.setCreateTime(date);
            service.create(data);
        }
        return maxCode + 1;
    }
    
    private String formatNumber(String number)
    {
        int numLen = number.length();
        String result = "";
        for (int i = numLen; i < 6; i++)
        {
            result += "0";
        }
        result += number;
        return result;
    }
}
