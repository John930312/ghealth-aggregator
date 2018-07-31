package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.SignInHistory;
import com.todaysoft.ghealth.mgmt.request.MaintainEmailRequest;
import com.todaysoft.ghealth.mgmt.request.QueryEmailRequest;
import com.todaysoft.ghealth.model.email.Email;
import com.todaysoft.ghealth.service.IEmailService;
import com.todaysoft.ghealth.service.wrapper.EmailWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private Gateway gateway;

    @Autowired
    private EmailWrapper wrapper;

    @Override
    public Pagination<Email> searcher(int pageNo, int pageSize)
    {
        QueryEmailRequest request = new QueryEmailRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Email> pagerResponse =
                gateway.request("/mgmt/email/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Email>>()
                {
                });
        Pager<com.todaysoft.ghealth.base.response.model.Email> pager = pagerResponse.getData();
        Pagination<Email> pagination = new Pagination<Email>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }


    @Override
    public Email get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Email> response =
                gateway.request("/mgmt/email/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Email>>()
                {
                });
        return wrapper.wrap(response.getData());
    }

    @Override
    public void create(Email data)
    {
        MaintainEmailRequest request = new MaintainEmailRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/email/create", request);
    }

    @Override
    public void modify(Email data)
    {
        MaintainEmailRequest request = new MaintainEmailRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/email/modify", request);
    }


    @Override
    public void delete(String id)
    {
        MaintainEmailRequest request = new MaintainEmailRequest();
        request.setId(id);
        gateway.request("/mgmt/email/delete", request);
    }


    @Override
    public List<Email> getList()
    {
        MaintainEmailRequest request = new MaintainEmailRequest();

        ListResponse<com.todaysoft.ghealth.base.response.model.Email> response =
                gateway.request("/mgmt/email/getList", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Email>>()
                {
                });
        return wrapper.wrap(response.getData());
    }


}
