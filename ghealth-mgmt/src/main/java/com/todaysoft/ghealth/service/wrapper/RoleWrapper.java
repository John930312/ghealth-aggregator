package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.role.Role;

@Component
public class RoleWrapper
{
    
    @Autowired
    private UserWrapper userWrapper;
    
    public List<Role> wrap(List<com.todaysoft.ghealth.base.response.model.Role> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Role data;
        List<Role> datas = new ArrayList<Role>();
        
        for (com.todaysoft.ghealth.base.response.model.Role record : records)
        {
            data = new Role();
            wrapRecord(record, data);
            datas.add(data);
        }
        
        return datas;
    }
    
    public Role wrap(com.todaysoft.ghealth.base.response.model.Role record)
    {
        if (null == record)
        {
            return null;
        }
        
        Role data = new Role();
        wrapRecord(record, data);
        return data;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Role source, Role target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime", "users");
        if (!CollectionUtils.isEmpty(source.getUsers()))
        {
            target.setUsers(userWrapper.wrap(source.getUsers()));
        }
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
    }
}
