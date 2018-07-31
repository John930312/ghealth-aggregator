package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.AgencyPrepay;

public interface IAgencyPrepayService
{
    void create(AgencyPrepay data);
    
    void modify(AgencyPrepay data);

    AgencyPrepay get(String id);
}
