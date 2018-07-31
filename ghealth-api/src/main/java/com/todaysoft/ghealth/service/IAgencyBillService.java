package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.AgencyBill;
import com.todaysoft.ghealth.mybatis.searcher.AgencyBillSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IAgencyBillService extends PagerQueryHandler<AgencyBill>
{
    void create(AgencyBill data);
    
    void modify(AgencyBill data);
    
    List<AgencyBill> list(AgencyBillSearcher searcher);
}
