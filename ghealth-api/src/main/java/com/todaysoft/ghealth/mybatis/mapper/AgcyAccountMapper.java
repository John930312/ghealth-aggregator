package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.searcher.AgencyAccountSearcher;

import java.util.List;

public interface AgcyAccountMapper {
    int count(AgencyAccountSearcher searcher);

    List<AgencyAccount> search(AgencyAccountSearcher searcher);

    void insert(AgencyAccount record);

    AgencyAccount get(String id);

    int modify(AgencyAccount record);
}
