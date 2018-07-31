package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.MaintainAccountRequest;
import com.todaysoft.ghealth.agcy.request.QueryAccountRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccount;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccountSearcher;
import com.todaysoft.ghealth.service.IAgencyAccountService;
import com.todaysoft.ghealth.service.wrapper.AgencyAccountWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class AgencyAccountService implements IAgencyAccountService {
    @Autowired
    private Gateway gateway;

    @Autowired
    private AgencyAccountWrapper agencyAccountWrapper;

    @Override
    public Pagination<AgencyAccount> searcher(AgencyAccountSearcher searcher, int pageNo, int pageSize)
    {
        QueryAccountRequest request = new QueryAccountRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyAccount> pagerResponse =
                gateway.request("/agcy/agencyAccount/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyAccount>>()
                {
                });
        Pager<com.todaysoft.ghealth.base.response.model.AgencyAccount> pager = pagerResponse.getData();
        Pagination<AgencyAccount> pagination = new Pagination<AgencyAccount>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(agencyAccountWrapper.wrap(pager.getRecords()));
        return pagination;
    }

    @Override
    public void create(AgencyAccount data)
    {
        MaintainAccountRequest request = new MaintainAccountRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/agencyAccount/create", request);
    }


    @Override
    public AgencyAccount get(AgencyAccount data)
    {
        MaintainAccountRequest request = new MaintainAccountRequest();
        request.setId(data.getId());
        ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyAccount> response =
                gateway.request("/agcy/agencyAccount/get", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyAccount>>()
                {
                });
        return agencyAccountWrapper.wrap(response.getData());
    }

    @Override
    public void modify(AgencyAccount data)
    {
        MaintainAccountRequest request = new MaintainAccountRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/agencyAccount/modify", request);
    }

    @Override
    public void change(AgencyAccount data)
    {
        MaintainAccountRequest request = new MaintainAccountRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/agencyAccount/change", request);
    }

    @Override
    public List<AgencyAccount> list(AgencyAccountSearcher searcher)
    {
        QueryAccountRequest request = new QueryAccountRequest();
        BeanUtils.copyProperties(searcher, request);
        ListResponse<com.todaysoft.ghealth.base.response.model.AgencyAccount> response =
                gateway.request("/agcy/agencyAccount/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.AgencyAccount>>()
                {
                });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }

        return agencyAccountWrapper.wrap(response.getData());
    }

    @Override
    public void delete(AgencyAccount data)
    {
        MaintainAccountRequest request = new MaintainAccountRequest();
        request.setId(data.getId());
        gateway.request("/agcy/agencyAccount/delete", request);
    }

    @Override
    public boolean isNameUnique(String id, String username)
    {
        MaintainAccountRequest request = new MaintainAccountRequest();
        request.setId(id);
        request.setUsername(username);

        ObjectResponse<Boolean> response = gateway.request("/agcy/agencyAccount/unique/name", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });

        if (null == response.getData())
        {
            return false;
        }

        return response.getData().booleanValue();
    }
}
