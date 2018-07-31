package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.email.Email;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface IEmailService {

    Pagination<Email> searcher(int pageNo, int pageSize);

    List<Email> getList();

    Email get(String id);

    void create(Email data);

    void modify(Email data);

    void delete(String id);
}
