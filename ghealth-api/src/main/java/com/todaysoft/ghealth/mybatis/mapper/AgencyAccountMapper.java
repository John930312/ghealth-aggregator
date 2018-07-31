package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgencyAccountMapper
{
    void insert(AgencyAccount data);
    
    void update(AgencyAccount data);
    
    AgencyAccount getAgencyPrimaryAccount(String agencyId);
    
    AgencyAccount getAccountByAccountId(String accountId);
    
    AgencyAccount getAccountByAccountUsername(String username);
    
    AgencyAccountToken getAccountTokenRecordByToken(String token);
    
    AgencyAccountToken getAccountTokenRecordByAccountId(String accountId);
    
    void insertAccountToken(AgencyAccountToken data);
    
    void updateAccountToken(AgencyAccountToken data);

    List<String> getAccountAuthorizedResources(String id);

    void updatePassword(AgencyAccount data);

    AgencyAccount getAccount(@Param("agencyId")String agencyId, @Param("primaryUsername")String primaryUsername);

    void updateUserPassword(AgencyAccount data);
}
