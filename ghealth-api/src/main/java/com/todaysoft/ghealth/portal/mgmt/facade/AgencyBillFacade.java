package com.todaysoft.ghealth.portal.mgmt.facade;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyBill;
import com.todaysoft.ghealth.mgmt.request.QueryAgencyBillRequest;
import com.todaysoft.ghealth.mybatis.searcher.AgencyBillSearcher;
import com.todaysoft.ghealth.service.IAgencyBillService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.AgencyBillWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class AgencyBillFacade
{
    @Autowired
    private IAgencyBillService service;
    
    @Autowired
    private AgencyBillWrapper wrapper;
    
    public PagerResponse<AgencyBill> pager(QueryAgencyBillRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        AgencyBillSearcher searcher = new AgencyBillSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapperSearcher(request, searcher);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyBill> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyBill>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.AgencyBill> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<AgencyBill> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<AgencyBill>(result);
    }
    
    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    
    private void wrapperSearcher(QueryAgencyBillRequest request, AgencyBillSearcher searcher)
    {
        if (StringUtils.isNotEmpty(request.getEndTime()))
        {
            searcher.setEndTime(transferLongToDate("yyyy-MM-dd 23:59:59", Long.valueOf(request.getEndTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartTime()))
        {
            searcher.setStartTime(transferLongToDate("yyyy-MM-dd 00:00:00", Long.valueOf(request.getStartTime())));
        }
    }
    
    public ListResponse<AgencyBill> list(QueryAgencyBillRequest request)
    {
        AgencyBillSearcher searcher = new AgencyBillSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapperSearcher(request, searcher);
        return new ListResponse<>(wrapper.wrap(service.list(searcher)));
    }
    
    public void create(com.todaysoft.ghealth.mybatis.model.AgencyBill data)
    {
        data.setId(IdGen.uuid());
        service.create(data);
    }

    public void create(com.todaysoft.ghealth.mybatis.model.AgencyBill data, Date date)
    {
        data.setBillTime(date);
        data.setId(IdGen.uuid());
        service.create(data);
    }
    
    public void modify(com.todaysoft.ghealth.mybatis.model.AgencyBill data)
    {
        data.setBillTime(new Date());
        service.modify(data);
    }
}
