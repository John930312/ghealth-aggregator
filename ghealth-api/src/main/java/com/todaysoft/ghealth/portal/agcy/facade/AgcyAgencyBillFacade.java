package com.todaysoft.ghealth.portal.agcy.facade;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.ghealth.agcy.request.QueryAgencyBillRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyBill;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.AgencyBillSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IAgencyBillService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.AgencyBillWrapper;

@Component
public class AgcyAgencyBillFacade
{
    @Autowired
    private IAgencyBillService service;
    
    @Autowired
    private AgencyBillWrapper wrapper;
    
    @Autowired
    private IAccountService accountService;
    
    public PagerResponse<AgencyBill> pager(QueryAgencyBillRequest request)
    {
        AgencyAccountDetails agencyAccountDetails = accountService.getAgencyAccountDetails(request.getToken());
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        AgencyBillSearcher searcher = new AgencyBillSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapperSearcher(request, searcher);
        searcher.setAgencyId(agencyAccountDetails.getAgencyId());
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyBill> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyBill>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.AgencyBill> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<com.todaysoft.ghealth.base.response.model.AgencyBill> result =
            Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<AgencyBill>(result);
    }
    
    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    
    private void wrapperSearcher(com.todaysoft.ghealth.agcy.request.QueryAgencyBillRequest request, AgencyBillSearcher searcher)
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
    
    @RequestMapping("/list")
    public ListResponse<AgencyBill> list(QueryAgencyBillRequest request)
    {
        AgencyAccountDetails agencyAccountDetails = accountService.getAgencyAccountDetails(request.getToken());
        AgencyBillSearcher searcher = new AgencyBillSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapperSearcher(request, searcher);
        searcher.setAgencyId(agencyAccountDetails.getAgencyId());
        return new ListResponse<>(wrapper.wrap(service.list(searcher)));
    }
}
