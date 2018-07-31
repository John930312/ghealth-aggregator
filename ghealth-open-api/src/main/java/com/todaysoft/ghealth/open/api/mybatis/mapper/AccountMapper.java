package com.todaysoft.ghealth.open.api.mybatis.mapper;

import com.todaysoft.ghealth.open.api.mybatis.model.Account;

public interface AccountMapper
{
    Account getManageAccount(String username);
    
    Account getAgencyAccount(String username);
}
