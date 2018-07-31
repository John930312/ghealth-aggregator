package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.agcy.request.MaintainAccountRequest;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyRole;
import com.todaysoft.ghealth.mybatis.searcher.AgencyAccountSearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IAgencyAccountService extends PagerQueryHandler<AgencyAccount> {

    List<AgencyAccount> search(AgencyAccountSearcher searcher);

    List<AgencyAccount> list(AgencyAccountSearcher searcher);

    void create(AgencyAccount data);

    AgencyAccount get(String id);

    List<AgencyRole> getRoles(UserRoleSearcher searcher);

    void modify(AgencyAccount data);

    boolean isNameUnique(MaintainAccountRequest request);
}
