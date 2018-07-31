package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Email;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IEmailService   extends PagerQueryHandler<Email>{
    Email get(String id);

    void modify(Email data);

    void create(Email data);

    List<Email> getList();

}
