package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.mybatis.model.AgencyPrepay;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IAgencyPrepayService;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class AgencyPrepayFacade
{
    @Autowired
    private IAgencyPrepayService service;
    
    @Autowired
    IAccountService accountService;
    
    public void create(AgencyPrepay data)
    {
        data.setCreateTime(new Date());
        data.setId(IdGen.uuid());
        service.create(data);
    }
}
