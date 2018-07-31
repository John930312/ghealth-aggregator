package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.AgencyRole;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class AgencyAccountWrapper {
    public List<AgencyAccount> wrap(List<com.todaysoft.ghealth.base.response.model.AgencyAccount> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }

        AgencyAccount agencyAccount;
        List<AgencyAccount> agencyAccounts = new ArrayList<AgencyAccount>();

        for (com.todaysoft.ghealth.base.response.model.AgencyAccount record : records)
        {
            agencyAccount = new AgencyAccount();
            wrapRecord(record, agencyAccount);
            agencyAccounts.add(agencyAccount);
        }

        return agencyAccounts;
    }

    public AgencyAccount wrap(com.todaysoft.ghealth.base.response.model.AgencyAccount record)
    {
        if (null == record)
        {
            return null;
        }

        AgencyAccount agencyAccount = new AgencyAccount();
        wrapRecord(record, agencyAccount);
        return agencyAccount;
    }

    private void wrapRecord(com.todaysoft.ghealth.base.response.model.AgencyAccount source, AgencyAccount target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
        List<AgencyRole> agencyRoles = source.getAgencyRoles();
        List<com.todaysoft.ghealth.model.role.Role> datas = new ArrayList<com.todaysoft.ghealth.model.role.Role>();

        if (!CollectionUtils.isEmpty(agencyRoles))
        {
            for (AgencyRole role : agencyRoles)
            {
                com.todaysoft.ghealth.model.role.Role data = new com.todaysoft.ghealth.model.role.Role();
                BeanUtils.copyProperties(role, data);
                datas.add(data);
            }
            target.setRoles(datas);
        }

    }
}
