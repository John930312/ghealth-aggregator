package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.model.role.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class RoleWrapper {
    @Autowired
    private AgencyAccountWrapper wrapper;

    public List<Role> wrap(List<com.todaysoft.ghealth.base.response.model.AgencyRole> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }

        Role data;
        List<Role> datas = new ArrayList<Role>();

        for (com.todaysoft.ghealth.base.response.model.AgencyRole record : records)
        {
            data = new Role();
            wrapRecord(record, data);
            datas.add(data);
        }

        return datas;
    }

    public Role wrap(com.todaysoft.ghealth.base.response.model.AgencyRole record)
    {
        if (null == record)
        {
            return null;
        }

        Role data = new Role();
        wrapRecord(record, data);
        return data;
    }

    private void wrapRecord(com.todaysoft.ghealth.base.response.model.AgencyRole source, Role target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime", "agencyAccounts");
        if (!CollectionUtils.isEmpty(source.getAgencyAccounts()))
        {
            target.setAgencyAccounts(wrapper.wrap(source.getAgencyAccounts()));
        }
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
    }
}
