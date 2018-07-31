package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.MaintainRoleRequest;
import com.todaysoft.ghealth.agcy.request.QueryRoleRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;


import com.todaysoft.ghealth.base.response.model.AuthorityNode;
import com.todaysoft.ghealth.model.role.Role;
import com.todaysoft.ghealth.model.role.RoleSearcher;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccount;
import com.todaysoft.ghealth.service.IRoleService;
import com.todaysoft.ghealth.service.wrapper.RoleWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private Gateway gateway;

    @Autowired
    private RoleWrapper roleWrapper;

    @Override
    public Pagination<Role> pagination(RoleSearcher searcher, int pageNo, int pageSize)
    {
        QueryRoleRequest request = new QueryRoleRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyRole> response =
                gateway.request("/agcy/role/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyRole>>()
                {
                });
        Pager<com.todaysoft.ghealth.base.response.model.AgencyRole> pager = response.getData();
        Pagination<Role> pagination = new Pagination<Role>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(roleWrapper.wrap(pager.getRecords()));
        return pagination;
    }

    @Override
    public Role get(Role data)
    {
        MaintainRoleRequest request = new MaintainRoleRequest();
        request.setId(data.getId());
        ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyRole> response =
                gateway.request("/agcy/role/get", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.AgencyRole>>()
                {
                });
        return roleWrapper.wrap(response.getData());
    }


    @Override
    public List<AuthorityNode> getAuthorityNodes()
    {
        QueryRoleRequest request = new QueryRoleRequest();
        ListResponse<AuthorityNode> response =
                gateway.request("/agcy/authority/getAuthorityNodes", request, new ParameterizedTypeReference<ListResponse<AuthorityNode>>()
                {
                });
        return response.getData();
    }

    @Override
    public void create(Role data)
    {
        MaintainRoleRequest request = new MaintainRoleRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/role/create", request);
    }

    @Override
    public void modify(Role data)
    {
        MaintainRoleRequest request = new MaintainRoleRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/role/modify", request);
    }

    @Override
    public boolean delete(String id)
    {
        MaintainRoleRequest request = new MaintainRoleRequest();
        request.setId(id);
        ObjectResponse<Boolean> response =
                gateway.request("/agcy/role/delete", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
                {
                });
        return response.getData();
    }

    @Override
    public boolean isNameUnique(String id,String name)
    {
        MaintainRoleRequest request = new MaintainRoleRequest();
        request.setName(name);
        request.setId(id);
        ObjectResponse<Boolean> response = gateway.request("/agcy/role/isNameUnique", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        if (null == response.getData())
        {
            return false;
        }
        return response.getData();
    }


    @Override
    public List<AgencyAccount> removeRepaeted(List<AgencyAccount> sources, List<AgencyAccount> targers)
    {
        Map<String, AgencyAccount> map = new HashMap<String, AgencyAccount>();
        List<AgencyAccount> agencyAccounts = new ArrayList<AgencyAccount>();
        if (!CollectionUtils.isEmpty(sources))
        {
            for (AgencyAccount source : sources)
            {
                map.put(source.getId(), source);
            }
        }
        if (!CollectionUtils.isEmpty(targers))
        {
            for (AgencyAccount source : targers)
            {
                if (null == map.get(source.getId()))
                {
                    agencyAccounts.add(source);
                }
            }
        }
        return agencyAccounts;
    }
    @Override
    public void addUser(Role data)
    {
        if (!CollectionUtils.isEmpty(data.getUserIds()))
        {
            String userId = "";
            for (String id : data.getUserIds())
            {
                userId += id + ",";
            }
            MaintainRoleRequest request = new MaintainRoleRequest();
            request.setId(data.getId());
            request.setUserId(userId);
            gateway.request("/agcy/role/addUser", request);
        }
    }

}
