package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.model.signInHistory.SignInHistory;
import com.todaysoft.ghealth.model.signInHistory.SignInHistorySearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ISendHistoryService
{
    Pagination<SignInHistory> search(SignInHistorySearcher searcher, int pageNo, int pageSize);
    
    List<SignInHistory> getSearch(String id);
}
