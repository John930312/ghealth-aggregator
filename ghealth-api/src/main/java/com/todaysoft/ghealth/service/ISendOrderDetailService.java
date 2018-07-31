package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.SignInHistory;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface ISendOrderDetailService extends PagerQueryHandler<SignInHistory> {
    List<SignInHistory> getSearch(String id);
}
