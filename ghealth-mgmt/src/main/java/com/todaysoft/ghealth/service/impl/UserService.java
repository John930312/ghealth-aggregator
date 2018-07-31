package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainUserRequest;
import com.todaysoft.ghealth.mgmt.request.QueryUserRequest;
import com.todaysoft.ghealth.model.user.User;
import com.todaysoft.ghealth.model.user.UserForm;
import com.todaysoft.ghealth.model.user.UserSearcher;
import com.todaysoft.ghealth.service.IUserService;
import com.todaysoft.ghealth.service.wrapper.UserWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class UserService implements IUserService
{
    
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private UserWrapper userWrapper;
    
    @Override
    public Pagination<User> searcher(UserSearcher searcher, int pageNo, int pageSize)
    {
        QueryUserRequest request = new QueryUserRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.User> pagerResponse =
            gateway.request("/mgmt/user/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.User>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.User> pager = pagerResponse.getData();
        Pagination<User> pagination = new Pagination<User>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(userWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public void create(UserForm data)
    {
        MaintainUserRequest request = new MaintainUserRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/user/create", request);
    }
    
    @Override
    public void delete(User data)
    {
        MaintainUserRequest request = new MaintainUserRequest();
        request.setId(data.getId());
        gateway.request("/mgmt/user/delete", request);
    }
    
    @Override
    public User get(User data)
    {
        MaintainUserRequest request = new MaintainUserRequest();
        request.setId(data.getId());
        ObjectResponse<com.todaysoft.ghealth.base.response.model.User> response =
            gateway.request("/mgmt/user/get", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.User>>()
            {
            });
        return userWrapper.wrap(response.getData());
    }
    
    @Override
    public void modify(UserForm data)
    {
        MaintainUserRequest request = new MaintainUserRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/user/modify", request);
    }
    
    @Override
    public List<User> list(UserSearcher searcher)
    {
        QueryUserRequest request = new QueryUserRequest();
        BeanUtils.copyProperties(searcher, request);
        ListResponse<com.todaysoft.ghealth.base.response.model.User> response =
            gateway.request("/mgmt/user/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.User>>()
            {
            });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        
        return userWrapper.wrap(response.getData());
    }
    
    @Override
    public void change(User data)
    {
        MaintainUserRequest request = new MaintainUserRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/user/change", request);
    }
    
    @Override
    public boolean isUsernameUnique(String username,String id)
    {
        MaintainUserRequest request = new MaintainUserRequest();
        
        request.setUsername(username);
        request.setId(id);
        
        ObjectResponse<Boolean> response = gateway.request("/mgmt/user/isUsernameUnique", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        
        return response.getData();
    }
}
