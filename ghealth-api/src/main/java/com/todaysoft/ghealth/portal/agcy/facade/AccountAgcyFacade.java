package com.todaysoft.ghealth.portal.agcy.facade;

import com.todaysoft.ghealth.agcy.request.MaintainAccountRequest;
import com.todaysoft.ghealth.agcy.request.QueryAccountRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.AgencyAccountSearcher;
import com.todaysoft.ghealth.service.IAccountRoleService;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IAgencyAccountService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.PasswordEncoder;
import com.todaysoft.ghealth.service.wrapper.AgencyAccountWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Component
public class AccountAgcyFacade {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAgencyAccountService agencyAccountService;

    @Autowired
    private AgencyAccountWrapper wrapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAccountRoleService accountRoleService;

    public PagerResponse<AgencyAccount> pager(@RequestBody QueryAccountRequest request)
    {

        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }

        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();

        AgencyAccountSearcher searcher = new AgencyAccountSearcher();
        searcher.setUsername(request.getUsername());
        searcher.setPhone(request.getPhone());
        searcher.setName(request.getName());
        searcher.setAgencyId(account.getAgencyId());
        searcher.setPrimaryAccount(false);

        PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyAccount> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyAccount>(agencyAccountService);
        Pager<com.todaysoft.ghealth.mybatis.model.AgencyAccount> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<AgencyAccount> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<AgencyAccount>(result);
    }


    public ListResponse<AgencyAccount> list(QueryAccountRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        AgencyAccountSearcher searcher = new AgencyAccountSearcher();
        BeanUtils.copyProperties(request, searcher);
        searcher.setAgencyId(account.getAgencyId());
        return new ListResponse<AgencyAccount>(wrapper.wrap(agencyAccountService.list(searcher)));
    }


    public void create(MaintainAccountRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }


        com.todaysoft.ghealth.mybatis.model.AgencyAccount agencyAccount = new com.todaysoft.ghealth.mybatis.model.AgencyAccount();
        agencyAccount.setId(IdGen.uuid());
        agencyAccount.setUsername(request.getUsername());
        agencyAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        agencyAccount.setName(request.getName());
        agencyAccount.setPhone(request.getPhone());
        agencyAccount.setEnabled(true);
        agencyAccount.setAgencyId(account.getAgencyId());
        agencyAccount.setDeleted(false);
        agencyAccount.setCreatorName(account.getAccountName());
        agencyAccount.setCreateTime(new Date());
        agencyAccountService.create(agencyAccount);
        com.todaysoft.ghealth.mybatis.model.UserRoleRelation userRoleRelation = new com.todaysoft.ghealth.mybatis.model.UserRoleRelation();
        if (!StringUtils.isEmpty(request.getRolePlatForm()))
        {
            String a = request.getRolePlatForm();
            String[] aa = a.split(",");
            for (int i = 0; i < aa.length; i++)
            {
                userRoleRelation.setUserId(agencyAccount.getId());
                userRoleRelation.setRoleId(aa[i]);
                accountRoleService.create(userRoleRelation);
            }
        }

    }


    public ObjectResponse<AgencyAccount> get(MaintainAccountRequest request)
    {
        return new ObjectResponse<AgencyAccount>(wrapper.wrap(agencyAccountService.get(request.getId())));
    }

    public void modify(MaintainAccountRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        com.todaysoft.ghealth.mybatis.model.AgencyAccount agencyAccount = agencyAccountService.get(request.getId());
        agencyAccount.setUsername(request.getUsername());
        agencyAccount.setPhone(request.getPhone());
        agencyAccount.setName(request.getName());
        agencyAccount.setUpdatorName(account.getAccountName());
        agencyAccount.setUpdateTime(new Date());
        if (!StringUtils.isEmpty(request.getPassword())){
            agencyAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        agencyAccountService.modify(agencyAccount);
        com.todaysoft.ghealth.mybatis.model.UserRoleRelation userRoleRelation = new com.todaysoft.ghealth.mybatis.model.UserRoleRelation();
        accountRoleService.deleteAccountRoleByUserId(request.getId());
        if (!StringUtils.isEmpty(request.getRolePlatForm()))
        {
            String a = request.getRolePlatForm();
            String[] aa = a.split(",");
            for (int i = 0; i < aa.length; i++)
            {
                userRoleRelation.setUserId(agencyAccount.getId());
                userRoleRelation.setRoleId(aa[i]);
                accountRoleService.create(userRoleRelation);
            }
        }

    }


    public void change(MaintainAccountRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.AgencyAccount data = new com.todaysoft.ghealth.mybatis.model.AgencyAccount();
        data = agencyAccountService.get(request.getId());
        data.setEnabled(request.getEnabled());
        agencyAccountService.modify(data);
    }

    public void delete(MaintainAccountRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        com.todaysoft.ghealth.mybatis.model.AgencyAccount data = agencyAccountService.get(request.getId());
        data.setDeleted(true);
        data.setDeletorName(account.getAccountName());
        data.setDeleteTime(new Date());
        agencyAccountService.modify(data);
        accountRoleService.deleteAccountRoleByUserId(data.getId());
    }

    public ObjectResponse<Boolean> isNameUnique(MaintainAccountRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        boolean unique = agencyAccountService.isNameUnique(request);
        return new ObjectResponse<Boolean>(unique);
    }

}
