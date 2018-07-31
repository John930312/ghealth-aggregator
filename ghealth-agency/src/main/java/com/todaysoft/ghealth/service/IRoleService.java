package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.AuthorityNode;
import com.todaysoft.ghealth.model.role.Role;
import com.todaysoft.ghealth.model.role.RoleSearcher;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccount;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface IRoleService {
    Pagination<Role> pagination(RoleSearcher searcher, int pageNo, int pageSize);

    Role get(Role data);

    List<AuthorityNode> getAuthorityNodes();

    void create(Role data);

    void modify(Role data);

    boolean delete(String id);

    boolean isNameUnique(String id,String name);

    List<AgencyAccount> removeRepaeted(List<AgencyAccount> sources, List<AgencyAccount> targers);

    void addUser(Role data);
}
