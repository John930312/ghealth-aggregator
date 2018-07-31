package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.Email;
import com.todaysoft.ghealth.mybatis.searcher.EmailSearcher;

import java.util.List;

public interface EmailMapper {
    int count(EmailSearcher searcher);

    List<Email> search(EmailSearcher searcher);

    Email get(String id);

    void create(Email data);

    void modify(Email data);

    List<Email> getList();
}
