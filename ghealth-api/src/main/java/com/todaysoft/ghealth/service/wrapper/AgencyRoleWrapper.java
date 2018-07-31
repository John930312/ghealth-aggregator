package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.AgencyRole;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.IAgcyRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AgencyRoleWrapper {

    @Autowired
    private IAgcyRoleService service;

    @Autowired
    private AgencyAccountWrapper wrapper;

    public List<AgencyRole> wrap(List<com.todaysoft.ghealth.mybatis.model.AgencyRole> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        records.get(0).getClass();
        AgencyRole role;
        List<AgencyRole> datas = new ArrayList<AgencyRole>();

        for (com.todaysoft.ghealth.mybatis.model.AgencyRole record : records)
        {
            role = new AgencyRole();
            wrap(record, role);
            datas.add(role);
        }

        return datas;
    }

    public AgencyRole wrap(com.todaysoft.ghealth.mybatis.model.AgencyRole record)
    {
        AgencyRole role = new AgencyRole();
        wrap(record, role);
        return role;
    }

    private void wrap(com.todaysoft.ghealth.mybatis.model.AgencyRole source, AgencyRole target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
        RoleAuthoritySearcher searcher = new RoleAuthoritySearcher();
        searcher.setRoleId(source.getId());
        List<Authority> roleAuthorities = service.getRoleAuthorities(searcher);
        target.setRoleAuthorities(roleAuthorities);
        UserRoleSearcher userRoleSearcher = new UserRoleSearcher();
        userRoleSearcher.setRoleId(source.getId());
        List<AgencyAccount> agencyAccounts = service.getAgencyAccount(userRoleSearcher);
        target.setAgencyAccounts(wrapper.wrap(agencyAccounts));
    }
}
