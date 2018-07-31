package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.Date;

import com.todaysoft.ghealth.service.impl.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.User;
import com.todaysoft.ghealth.mgmt.request.MaintainUserRequest;
import com.todaysoft.ghealth.mgmt.request.QueryUserRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.UserSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IUserRoleService;
import com.todaysoft.ghealth.service.IUserService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.UserWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class UserFacade
{
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserRoleService userRoleService;
    
    @Autowired
    private UserWrapper userWrapper;
    
    @Autowired
    private IAccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public PagerResponse<User> pager(@RequestBody QueryUserRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        UserSearcher searcher = new UserSearcher();
        searcher.setUsername(request.getUsername());
        searcher.setPhone(request.getPhone());
        searcher.setName(request.getName());
        searcher.setBuiltin(false);
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.User> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.User>(userService);
        Pager<com.todaysoft.ghealth.mybatis.model.User> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<User> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), userWrapper.wrap(pager.getRecords()));
        return new PagerResponse<User>(result);
    }
    
    public ListResponse<User> list(QueryUserRequest request)
    {
        UserSearcher searcher = new UserSearcher();
        BeanUtils.copyProperties(request, searcher);
        return new ListResponse<User>(userWrapper.wrap(userService.list(searcher)));
    }
    
    public void create(MaintainUserRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.User user = new com.todaysoft.ghealth.mybatis.model.User();
        user.setId(IdGen.uuid());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setDeleted(false);
        user.setCreatorName(account.getName());
        user.setCreateTime(new Date());
        userService.create(user);
        com.todaysoft.ghealth.mybatis.model.UserRoleRelation userRoleRelation = new com.todaysoft.ghealth.mybatis.model.UserRoleRelation();
        if (!StringUtils.isEmpty(request.getRolePlatForm()))
        {
            String a = request.getRolePlatForm();
            String[] aa = a.split(",");
            for (int i = 0; i < aa.length; i++)
            {
                userRoleRelation.setUserId(user.getId());
                userRoleRelation.setRoleId(aa[i]);
                userRoleService.create(userRoleRelation);
            }
        }
        
    }
    
    public void modify(MaintainUserRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.User user = userService.get(request.getId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setName(request.getName());
        user.setUpdatorName(account.getName());
        user.setUpdateTime(new Date());
        if (!StringUtils.isEmpty(request.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        userService.modify(user);
        com.todaysoft.ghealth.mybatis.model.UserRoleRelation userRoleRelation = new com.todaysoft.ghealth.mybatis.model.UserRoleRelation();
        userRoleService.deleteByuserId(request.getId());
        if (!StringUtils.isEmpty(request.getRolePlatForm()))
        {
            String a = request.getRolePlatForm();
            String[] aa = a.split(",");
            for (int i = 0; i < aa.length; i++)
            {
                userRoleRelation.setUserId(user.getId());
                userRoleRelation.setRoleId(aa[i]);
                userRoleService.create(userRoleRelation);
            }
        }
        
    }
    
    public ObjectResponse<User> get(MaintainUserRequest request)
    {
        return new ObjectResponse<User>(userWrapper.wrap(userService.get(request.getId())));
    }
    
    public void delete(MaintainUserRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.User data = userService.get(request.getId());
        data.setDeleted(true);
        data.setDeletorName(account.getName());
        data.setDeleteTime(new Date());
        userService.modify(data);
        userRoleService.deleteByuserId(data.getId());
    }
    
    public void change(MaintainUserRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.User data = new com.todaysoft.ghealth.mybatis.model.User();
        data = userService.get(request.getId());
        data.setEnabled(request.getEnabled());
        userService.modify(data);
    }
    
    public ObjectResponse<Boolean> isUsernameUnique(MaintainUserRequest request)
    {
        boolean unique = userService.isUsernameUnique(request.getUsername(),request.getId());
        return new ObjectResponse<Boolean>(unique);
    }
    
}
