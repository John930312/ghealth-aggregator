package com.todaysoft.ghealth.service.impl;

import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainAgencyRequest;
import com.todaysoft.ghealth.mgmt.request.QueryAgenciesRequest;
import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencyDetails;
import com.todaysoft.ghealth.model.agency.AgencyForm;
import com.todaysoft.ghealth.model.agency.AgencySearcher;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.wrapper.AgencyWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class AgencyService implements IAgencyService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private AgencyWrapper agencyWrapper;
    
    @Override
    public Pagination<Agency> searcher(AgencySearcher searcher, int pageNo, int pageSize)
    {
        QueryAgenciesRequest request = agencyWrapper.wrapperSearcher(searcher);
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Agency> response =
            gateway.request("/mgmt/agencies/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Agency>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Agency> pager = response.getData();
        Pagination<Agency> pagination = new Pagination<Agency>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(agencyWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public List<Agency> list(AgencySearcher searcher)
    {
        QueryAgenciesRequest request = new QueryAgenciesRequest();
        BeanUtils.copyProperties(searcher, request);
        ListResponse<com.todaysoft.ghealth.base.response.model.Agency> response =
            gateway.request("/mgmt/agencies/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Agency>>()
            {
            });
        List<com.todaysoft.ghealth.base.response.model.Agency> records = response.getData();
        return agencyWrapper.wrap(records);
    }
    
    @Override
    public AgencyDetails get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyDetails> response =
            gateway.request("/mgmt/agencies/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyDetails>>()
            {
            });
        return agencyWrapper.wrap(response.getData());
    }
    
    @Override
    public boolean isUsernameUnique(String username)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        request.setPrimaryUsername(username);
        
        ObjectResponse<Boolean> response = gateway.request("/mgmt/agencies/unique/username", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        
        if (null == response.getData())
        {
            return false;
        }
        
        return response.getData().booleanValue();
    }
    
    @Override
    public boolean isCodeUnique(String id, String code)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        request.setId(id);
        request.setCode(code);
        
        ObjectResponse<Boolean> response = gateway.request("/mgmt/agencies/unique/code", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        
        if (null == response.getData())
        {
            return false;
        }
        
        return response.getData().booleanValue();
    }
    
    @Override
    public void create(AgencyForm data)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/agencies/create", request);
    }
    
    @Override
    public void modify(AgencyForm data)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/agencies/modify", request);
    }
    
    @Override
    public boolean delete(String id)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        request.setId(id);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/agencies/delete", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public void recharge(AgencyForm data)
    {
        MaintainAgencyRequest request = new MaintainAgencyRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/agencies/recharge", request);
    }




}
