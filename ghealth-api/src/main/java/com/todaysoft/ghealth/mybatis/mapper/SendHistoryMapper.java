package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.SignInHistory;
import com.todaysoft.ghealth.mybatis.searcher.SignInHistorySearcher;

import java.util.List;

public interface SendHistoryMapper {
    List<SignInHistory> search(SignInHistorySearcher searcher);

    int count(SignInHistorySearcher searcher);

}
