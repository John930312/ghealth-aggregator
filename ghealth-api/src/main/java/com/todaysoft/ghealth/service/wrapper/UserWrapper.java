package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.User;
import com.todaysoft.ghealth.mybatis.model.Role;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.IUserService;

@Component
public class UserWrapper
{
    @Autowired
    private IUserService userService;
    
    public List<User> wrap(List<com.todaysoft.ghealth.mybatis.model.User> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        records.get(0).getClass();
        User User;
        List<User> datas = new ArrayList<User>();
        
        for (com.todaysoft.ghealth.mybatis.model.User record : records)
        {
            User = new User();
            wrap(record, User);
            datas.add(User);
        }
        
        return datas;
    }
    
    public User wrap(com.todaysoft.ghealth.mybatis.model.User record)
    {
        User User = new User();
        wrap(record, User);
        return User;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.User source, User target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
        UserRoleSearcher userRoleSearcher = new UserRoleSearcher();
        userRoleSearcher.setUserId(source.getId());
        List<Role> roles = userService.getRoles(userRoleSearcher);
        List<com.todaysoft.ghealth.base.response.model.Role> datas = new ArrayList<com.todaysoft.ghealth.base.response.model.Role>();
        if (!CollectionUtils.isEmpty(roles))
        {
            for (Role role : roles)
            {
                com.todaysoft.ghealth.base.response.model.Role data = new com.todaysoft.ghealth.base.response.model.Role();
                BeanUtils.copyProperties(role, data, "createTime", "updateTime", "deleteTime");
                data.setCreateTime(null == role.getCreateTime() ? null : role.getCreateTime().getTime());
                data.setUpdateTime(null == role.getUpdateTime() ? null : role.getUpdateTime().getTime());
                data.setDeleteTime(null == role.getDeleteTime() ? null : role.getDeleteTime().getTime());
                datas.add(data);
            }
        }
        target.setRoles(datas);
    }
}
