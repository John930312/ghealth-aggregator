package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AgencyBill;
import com.todaysoft.ghealth.mybatis.searcher.AgencyBillSearcher;

import java.util.List;

public interface AgencyBillMapper
{
    int delete(String id);
    
    int create(AgencyBill record);
    
    AgencyBill get(String id);
    
    int update(AgencyBill record);
    
    int count(AgencyBillSearcher searcher);
    
    List<AgencyBill> search(AgencyBillSearcher searcher);
    
}