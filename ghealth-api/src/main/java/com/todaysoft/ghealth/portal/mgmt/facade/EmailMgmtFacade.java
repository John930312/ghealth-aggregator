package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Email;
import com.todaysoft.ghealth.mgmt.request.MaintainEmailRequest;

import com.todaysoft.ghealth.mgmt.request.QueryEmailRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.EmailSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IEmailService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.EmailWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Component
public class EmailMgmtFacade {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IEmailService service;

    @Autowired
    private EmailWrapper wrapper;


    public PagerResponse<Email> pager(@RequestBody QueryEmailRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        EmailSearcher searcher = new EmailSearcher();

        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Email> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Email>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Email> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Email> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<Email>(result);
    }


    public ObjectResponse<Email> get(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Email email = service.get(request.getId());
        Email data = wrapper.wrap(email);
        return new ObjectResponse<Email>(data);
    }


    public void create(MaintainEmailRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        com.todaysoft.ghealth.mybatis.model.Email email = new com.todaysoft.ghealth.mybatis.model.Email();
        BeanUtils.copyProperties(request, email);
        email.setId(IdGen.uuid());
        email.setCreateTime(new Date());
        email.setCreatorName(account.getName());
        email.setDeleted(false);
        service.create(email);
    }

    public void modify(MaintainEmailRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        com.todaysoft.ghealth.mybatis.model.Email email = new com.todaysoft.ghealth.mybatis.model.Email();
        BeanUtils.copyProperties(request, email);
        email.setUpdatorName(account.getName());
        email.setUpdateTime(new Date());
        service.modify(email);
    }


    public void delete(MaintainEmailRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        com.todaysoft.ghealth.mybatis.model.Email email = service.get(request.getId());
        email.setDeleted(true);
        email.setDeletorName(account.getName());
        email.setDeleteTime(new Date());
        service.modify(email);
    }


    public ListResponse<Email> getList(MaintainEmailRequest request)
    {
        List<com.todaysoft.ghealth.mybatis.model.Email> emails = service.getList();
        return new ListResponse<Email>(wrapper.wrap(emails));
    }

}
