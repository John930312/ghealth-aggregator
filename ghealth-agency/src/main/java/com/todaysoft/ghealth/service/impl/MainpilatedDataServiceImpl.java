package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.MaintainAccountRequest;
import com.todaysoft.ghealth.agcy.request.QueryOrdersRequest;
import com.todaysoft.ghealth.service.IMainpulatedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainpilatedDataServiceImpl implements IMainpulatedDataService
{
    @Autowired
    private Gateway gateway;

    @Override
    public void mainpulatedData()
    {
        QueryOrdersRequest request = new QueryOrdersRequest();
        request.setLogin(false);
        gateway.request("/mgmt/template/mainpulatedData",request);
    }
}
