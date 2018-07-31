package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.Role;
import com.todaysoft.ghealth.model.user.User;

@Component
public class UserWrapper
{
    public List<User> wrap(List<com.todaysoft.ghealth.base.response.model.User> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        User user;
        List<User> Users = new ArrayList<User>();
        
        for (com.todaysoft.ghealth.base.response.model.User record : records)
        {
            user = new User();
            wrapRecord(record, user);
            Users.add(user);
        }
        
        return Users;
    }
    
    public User wrap(com.todaysoft.ghealth.base.response.model.User record)
    {
        if (null == record)
        {
            return null;
        }
        
        User user = new User();
        wrapRecord(record, user);
        return user;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.User source, User target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
        List<Role> roles = source.getRoles();
        List<com.todaysoft.ghealth.model.role.Role> datas = new ArrayList<com.todaysoft.ghealth.model.role.Role>();
        
        if (!CollectionUtils.isEmpty(roles))
        {
            for (Role role : roles)
            {
                com.todaysoft.ghealth.model.role.Role data = new com.todaysoft.ghealth.model.role.Role();
                BeanUtils.copyProperties(role, data);
                datas.add(data);
            }
            target.setRoles(datas);
        }
        
    }
}
