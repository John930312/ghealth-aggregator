package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.base.response.model.Role;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.IRoleService;

@Component
public class RoleWrapper
{
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private UserWrapper userWrapper;
    
    public List<Role> wrap(List<com.todaysoft.ghealth.mybatis.model.Role> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        records.get(0).getClass();
        Role role;
        List<Role> datas = new ArrayList<Role>();
        
        for (com.todaysoft.ghealth.mybatis.model.Role record : records)
        {
            role = new Role();
            wrap(record, role);
            datas.add(role);
        }
        
        return datas;
    }
    
    public Role wrap(com.todaysoft.ghealth.mybatis.model.Role record)
    {
        Role role = new Role();
        wrap(record, role);
        return role;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Role source, Role target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
        RoleAuthoritySearcher searcher = new RoleAuthoritySearcher();
        searcher.setRoleId(source.getId());
        List<Authority> roleAuthorities = roleService.getRoleAuthorities(searcher);
        target.setRoleAuthorities(roleAuthorities);
        UserRoleSearcher userRoleSearcher = new UserRoleSearcher();
        userRoleSearcher.setRoleId(source.getId());
        List<User> users = roleService.getUsers(userRoleSearcher);
        target.setUsers(userWrapper.wrap(users));
    }
}
