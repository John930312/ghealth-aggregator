package com.todaysoft.ghealth.service;

import java.util.List;


import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.searcher.AgencySearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;
import com.todaysoft.ghealth.service.model.AgencyAccountLoginDetails;
import org.apache.ibatis.annotations.Param;

public interface IAgencyService extends PagerQueryHandler<Agency>
{
    Agency get(String id);
    
    boolean isUsernameUnique(String username);
    
    boolean isCodeUnique(String id, String code);
    
    void create(Agency data, AgencyAccount accountData);
    
    void modify(Agency data, AgencyAccount accountData);
    
    AgencyAccount getPrimaryAccount(String id);
    
    AgencyAccountLoginDetails login(String username, String password);
    
    List<Agency> list(AgencySearcher searcher);

    void modifyPassword(AgencyAccount accountData);

    AgencyAccount getAccount(@Param("agencyId")String agencyId, @Param("primaryUsername")String primaryUsername);

    List<String> getSubAccount(String id);

    void modifyUserPassword(AgencyAccount accountData);

    Agency getReportData(String id);
}
