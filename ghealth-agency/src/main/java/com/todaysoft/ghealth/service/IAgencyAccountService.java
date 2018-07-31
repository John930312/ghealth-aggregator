package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.agencyAccount.AgencyAccount;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccountSearcher;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface IAgencyAccountService {
    Pagination<AgencyAccount> searcher(AgencyAccountSearcher searcher, int pageNo, int pageSize);

    void create(AgencyAccount data);

    AgencyAccount get(AgencyAccount data);

    void modify(AgencyAccount data);

    void change(AgencyAccount data);

    List<AgencyAccount> list(AgencyAccountSearcher searcher);

    void delete(AgencyAccount data);

    boolean isNameUnique(String id, String username);
}
