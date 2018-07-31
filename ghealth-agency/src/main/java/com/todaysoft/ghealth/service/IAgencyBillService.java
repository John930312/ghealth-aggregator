package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.agency.AgencyBill;
import com.todaysoft.ghealth.model.agency.AgencyBillSearcher;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface IAgencyBillService
{
    Pagination<AgencyBill> searcher(AgencyBillSearcher searcher, int pageNo, int pageSize);
    
    List<AgencyBill> list(AgencyBillSearcher searcher);
}
