package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AgencyPrepay;

public interface AgencyPrepayMapper
{
    int delete(String id);
    
    int insert(AgencyPrepay record);
    
    AgencyPrepay get(String id);
    
    int update(AgencyPrepay record);

}