package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyRole;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.IAgencyAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AgencyAccountWrapper {
//    @Autowired
//    private IUserService userService;

    @Autowired
    private IAgencyAccountService service;

    public List<AgencyAccount> wrap(List<com.todaysoft.ghealth.mybatis.model.AgencyAccount> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        records.get(0).getClass();
        AgencyAccount agencyAccount;
        List<AgencyAccount> datas = new ArrayList<AgencyAccount>();

        for (com.todaysoft.ghealth.mybatis.model.AgencyAccount record : records)
        {
            agencyAccount = new AgencyAccount();
            wrap(record, agencyAccount);
            datas.add(agencyAccount);
        }

        return datas;
    }

    public AgencyAccount wrap(com.todaysoft.ghealth.mybatis.model.AgencyAccount record)
    {
        AgencyAccount agencyAccount = new AgencyAccount();
        wrap(record, agencyAccount);
        return agencyAccount;
    }

    private void wrap(com.todaysoft.ghealth.mybatis.model.AgencyAccount source, AgencyAccount target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
        UserRoleSearcher userRoleSearcher = new UserRoleSearcher();
        userRoleSearcher.setUserId(source.getId());
        List<AgencyRole> agencyRoles = service.getRoles(userRoleSearcher);
        List<com.todaysoft.ghealth.base.response.model.AgencyRole> datas = new ArrayList<com.todaysoft.ghealth.base.response.model.AgencyRole>();
        if (!CollectionUtils.isEmpty(agencyRoles))
        {
            for (AgencyRole agencyRole : agencyRoles)
            {
                com.todaysoft.ghealth.base.response.model.AgencyRole data = new com.todaysoft.ghealth.base.response.model.AgencyRole();
                BeanUtils.copyProperties(agencyRole, data, "createTime", "updateTime", "deleteTime");
                data.setCreateTime(null == agencyRole.getCreateTime() ? null : agencyRole.getCreateTime().getTime());
                data.setUpdateTime(null == agencyRole.getUpdateTime() ? null : agencyRole.getUpdateTime().getTime());
                data.setDeleteTime(null == agencyRole.getDeleteTime() ? null : agencyRole.getDeleteTime().getTime());
                datas.add(data);
            }
        }
        target.setAgencyRoles(datas);
    }
}
