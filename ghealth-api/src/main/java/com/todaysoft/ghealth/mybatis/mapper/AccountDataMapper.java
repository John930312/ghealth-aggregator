package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AccountData;
import com.todaysoft.ghealth.mybatis.model.AccountToken;
import com.todaysoft.ghealth.mybatis.searcher.AccountDataSearcher;
import com.todaysoft.ghealth.mybatis.searcher.AccountTokenSearcher;

import java.util.List;

public interface AccountDataMapper
{
    int createAccount(AccountData record);
    
    int createAccountToken(AccountToken record);

    AccountData getAccountById(String id);
    
    AccountToken getAccountToken(AccountTokenSearcher searcher);
    
    List<AccountData> findAccountData(AccountDataSearcher searcher);

    int modifyAccount(AccountData record);
    
    int modifyAccountToken(AccountToken record);
    
}