package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AgencyRole;
import com.todaysoft.ghealth.mybatis.searcher.AgencyRoleSearcher;

import java.util.List;

public interface AgencyRoleMapper {

    int count(AgencyRoleSearcher searcher);

    List<AgencyRole> search(AgencyRoleSearcher searcher);

    AgencyRole get(String id);

    int insert(AgencyRole record);

    int update(AgencyRole record);
}
