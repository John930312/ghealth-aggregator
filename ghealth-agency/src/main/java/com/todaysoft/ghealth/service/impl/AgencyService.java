package com.todaysoft.ghealth.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainAgencyRequest;
import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.service.IAgencyService;

@Service
public class AgencyService implements IAgencyService
{
    @Autowired
    private Gateway gateway;
    
    @Override
    public Agency get(String id)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Agency> response =
            gateway.request("/agcy/agency/get", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Agency>>()
            {
            });
        Agency data = new Agency();
        if (null == response.getData())
        {
            return data;
        }
        
        BeanUtils.copyProperties(response.getData(), data, "createTime", "updateTime", "deleteTime");
        data.setCreateTime(null == response.getData().getCreateTime() ? null : new Date(response.getData().getCreateTime()));
        return data;
    }
    
    @Override
    public void modify(Agency data)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/agency/modify", request);
    }


    @Override
    public void modifyPassword(Agency data)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        BeanUtils.copyProperties(data, request);
        request.setPrimaryAccount(Boolean.valueOf(data.getPrimaryAccount()));
        gateway.request("/agcy/agency/changePassword", request);
    }
}
