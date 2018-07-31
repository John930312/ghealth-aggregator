package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.SignInHistory;
import com.todaysoft.ghealth.mybatis.searcher.SignInOrderDetailSearcher;

import java.util.List;

public interface SendOrderDetailMapper {
    List<SignInHistory> search(SignInOrderDetailSearcher searcher);

    int count(SignInOrderDetailSearcher searcher);

    List<SignInHistory> getSearch(String id);
}
