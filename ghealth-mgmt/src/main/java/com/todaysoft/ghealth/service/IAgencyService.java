package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencyDetails;
import com.todaysoft.ghealth.model.agency.AgencyForm;
import com.todaysoft.ghealth.model.agency.AgencySearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface IAgencyService
{
    Pagination<Agency> searcher(AgencySearcher searcher, int pageNo, int pageSize);
    
    List<Agency> list(AgencySearcher searcher);
    
    AgencyDetails get(String id);
    
    boolean isUsernameUnique(String username);
    
    boolean isCodeUnique(String id, String code);
    
    void create(AgencyForm data);
    
    void modify(AgencyForm data);
    
    boolean delete(String id);
    
    void recharge(AgencyForm data);


}
