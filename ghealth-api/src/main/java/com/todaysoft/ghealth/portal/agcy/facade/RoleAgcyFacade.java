package com.todaysoft.ghealth.portal.agcy.facade;

import com.todaysoft.ghealth.agcy.request.MaintainRoleRequest;
import com.todaysoft.ghealth.agcy.request.QueryRoleRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyRole;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountRole;
import com.todaysoft.ghealth.mybatis.model.AgencyRoleAuthority;
import com.todaysoft.ghealth.mybatis.searcher.AgencyRoleSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IAgcyRoleService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.AgencyRoleWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RoleAgcyFacade {
    @Autowired
    private IAgcyRoleService roleService;

    @Autowired
    private AgencyRoleWrapper roleWrapper;

    @Autowired
    private IAccountService accountService;

    public PagerResponse<AgencyRole> pager(QueryRoleRequest request)
    {

        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }

        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        AgencyRoleSearcher searcher = new AgencyRoleSearcher();
        searcher.setName(request.getName());
        searcher.setAgencyId(account.getAgencyId());
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyRole> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyRole>(roleService);
        Pager<com.todaysoft.ghealth.mybatis.model.AgencyRole> pager = queryer.query(searcher, pageNo, pageSize);
        List<com.todaysoft.ghealth.mybatis.model.AgencyRole> records = pager.getRecords();
        Pager<AgencyRole> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), roleWrapper.wrap(records));
        return new PagerResponse<AgencyRole>(result);
    }


    public ObjectResponse<AgencyRole> get(MaintainRoleRequest request)
    {
        return new ObjectResponse<AgencyRole>(roleWrapper.wrap(roleService.get(request.getId())));
    }


    public void create(MaintainRoleRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.AgencyRole data = new com.todaysoft.ghealth.mybatis.model.AgencyRole();
        BeanUtils.copyProperties(request, data);
        data.setId(IdGen.uuid());
        data.setAgencyId(account.getAgencyId());
        data.setCreatorName(account.getAccountName());
        data.setCreateTime(new Date());
        data.setDeleted(false);
        roleService.create(data);
        insertRoleAuthoirties(request.getAuthorityIds(), data);
    }

    private void insertRoleAuthoirties(String authorityIds, com.todaysoft.ghealth.mybatis.model.AgencyRole data)
    {
        if (StringUtils.isNotEmpty(authorityIds))
        {
            for (String authorityId : authorityIds.split(","))
            {
                AgencyRoleAuthority agencyRoleAuthority = new AgencyRoleAuthority();
                Authority authority = new Authority();
                authority.setId(authorityId);
                agencyRoleAuthority.setAgencyRole(data);
                agencyRoleAuthority.setAuthority(authority);
                roleService.insertRoleAuthority(agencyRoleAuthority);
            }
        }
    }


    public void modify(MaintainRoleRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.AgencyRole data = new com.todaysoft.ghealth.mybatis.model.AgencyRole();
        BeanUtils.copyProperties(request, data);
        data.setUpdateTime(new Date());
        data.setUpdatorName(account.getAccountName());
        roleService.modify(data);
        roleService.deleteRoleAuthority(data.getId());
        insertRoleAuthoirties(request.getAuthorityIds(), data);
    }

    public ObjectResponse<Boolean> delete(MaintainRoleRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getId()))
        {
            throw new IllegalStateException();
        }
        int count = roleService.countById(request.getId());
        if (count == 0)
        {
            com.todaysoft.ghealth.mybatis.model.AgencyRole data = new com.todaysoft.ghealth.mybatis.model.AgencyRole();
            BeanUtils.copyProperties(request, data);
            data.setDeleted(true);
            data.setDeletorName(account.getAccountName());
            data.setDeleteTime(new Date());
            roleService.modify(data);
            roleService.deleteRoleAuthority(data.getId());
            roleService.deleteAccountRole(data.getId());
            return new ObjectResponse<>(true);
        }
        else
        {
            return new ObjectResponse<>(false);
        }
    }

    public ObjectResponse<Boolean> isNameUnique(MaintainRoleRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        request.setAgencyId(account.getAgencyId());
        boolean unique = roleService.isNameUnique(request);
        return new ObjectResponse<Boolean>(unique);
    }

    public void addUser(MaintainRoleRequest request)
    {
        if (StringUtils.isNotEmpty(request.getUserId()))
        {
            //            roleService.deleteUserRole(request.getId());
            for (String userId : request.getUserId().split(","))
            {
                if (StringUtils.isNotEmpty(userId))
                {
                    AgencyAccountRole agencyAccountRole = new AgencyAccountRole();
                    AgencyAccount agencyAccount = new AgencyAccount();
                    agencyAccount.setId(userId);
                    agencyAccountRole.setAgencyAccount(agencyAccount);
                    com.todaysoft.ghealth.mybatis.model.AgencyRole agencyRole = new com.todaysoft.ghealth.mybatis.model.AgencyRole(request.getId());
                    agencyAccountRole.setAgencyRole(agencyRole);
                    roleService.insertAccountRole(agencyAccountRole);
                }
            }
        }
    }
}
