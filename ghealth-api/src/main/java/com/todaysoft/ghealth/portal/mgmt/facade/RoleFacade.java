package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.base.response.model.Role;
import com.todaysoft.ghealth.mgmt.request.MaintainRoleRequest;
import com.todaysoft.ghealth.mgmt.request.QueryRoleRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.RoleAuthority;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.model.UserRole;
import com.todaysoft.ghealth.mybatis.searcher.RoleSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IRoleService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.RoleWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class RoleFacade
{
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private RoleWrapper roleWrapper;
    
    @Autowired
    private IAccountService accountService;
    
    public PagerResponse<Role> pager(QueryRoleRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        RoleSearcher searcher = new RoleSearcher();
        searcher.setName(request.getName());
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Role> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Role>(roleService);
        Pager<com.todaysoft.ghealth.mybatis.model.Role> pager = queryer.query(searcher, pageNo, pageSize);
        List<com.todaysoft.ghealth.mybatis.model.Role> records = pager.getRecords();
        Pager<Role> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), roleWrapper.wrap(records));
        return new PagerResponse<Role>(result);
    }
    
    public ListResponse<Role> list(QueryRoleRequest request)
    {
        RoleSearcher searcher = new RoleSearcher();
        searcher.setName(request.getName());
        return new ListResponse<Role>(roleWrapper.wrap(roleService.search(searcher)));
    }
    
    public ObjectResponse<Role> get(MaintainRoleRequest request)
    {
        return new ObjectResponse<Role>(roleWrapper.wrap(roleService.get(request.getId())));
    }
    
    //    public void delete(MaintainRoleRequest request)
    //    {
    //        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
    //        com.todaysoft.ghealth.mybatis.model.Role data = new com.todaysoft.ghealth.mybatis.model.Role();
    //        BeanUtils.copyProperties(request, data);
    //        data.setDeleted(true);
    //        data.setDeletorName(account.getName());
    //        data.setDeleteTime(new Date());
    //        roleService.modify(data);
    //        roleService.deleteRoleAuthority(data.getId());
    //        roleService.deleteUserRole(data.getId());
    //    }
    public ObjectResponse<Boolean> delete(MaintainRoleRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getId()))
        {
            throw new IllegalStateException();
        }
        int count = roleService.countById(request.getId());
        if (count == 0)
        {
            com.todaysoft.ghealth.mybatis.model.Role data = new com.todaysoft.ghealth.mybatis.model.Role();
            BeanUtils.copyProperties(request, data);
            data.setDeleted(true);
            data.setDeletorName(account.getName());
            data.setDeleteTime(new Date());
            roleService.modify(data);
            roleService.deleteRoleAuthority(data.getId());
            roleService.deleteUserRole(data.getId());
            return new ObjectResponse<>(true);
        }
        else
        {
            return new ObjectResponse<>(false);
        }
    }
    
    public void modify(MaintainRoleRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.Role data = new com.todaysoft.ghealth.mybatis.model.Role();
        BeanUtils.copyProperties(request, data);
        data.setUpdateTime(new Date());
        data.setUpdatorName(account.getName());
        roleService.modify(data);
        roleService.deleteRoleAuthority(data.getId());
        insertRoleAuthoirties(request.getAuthorityIds(), data);
    }
    
    public void create(MaintainRoleRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.Role data = new com.todaysoft.ghealth.mybatis.model.Role();
        BeanUtils.copyProperties(request, data);
        data.setId(IdGen.uuid());
        data.setCreatorName(account.getName());
        data.setCreateTime(new Date());
        data.setDeleted(false);
        roleService.create(data);
        insertRoleAuthoirties(request.getAuthorityIds(), data);
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
                    UserRole userRole = new UserRole();
                    User user = new User();
                    user.setId(userId);
                    userRole.setUser(user);
                    com.todaysoft.ghealth.mybatis.model.Role role = new com.todaysoft.ghealth.mybatis.model.Role(request.getId());
                    userRole.setRole(role);
                    roleService.insertUserRole(userRole);
                }
            }
        }
    }
    
    private void insertRoleAuthoirties(String authorityIds, com.todaysoft.ghealth.mybatis.model.Role data)
    {
        if (StringUtils.isNotEmpty(authorityIds))
        {
            for (String authorityId : authorityIds.split(","))
            {
                RoleAuthority roleAuthority = new RoleAuthority();
                Authority authority = new Authority();
                authority.setId(authorityId);
                roleAuthority.setRole(data);
                roleAuthority.setAuthority(authority);
                roleService.insertRoleAuthority(roleAuthority);
            }
        }
    }
    
    public ObjectResponse<Boolean> isNameUnique(MaintainRoleRequest request)
    {
        boolean unique = roleService.isNameUnique(request.getId(), request.getName());
        return new ObjectResponse<Boolean>(unique);
    }
}
