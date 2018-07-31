package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.EmailMapper;
import com.todaysoft.ghealth.mybatis.model.Email;
import com.todaysoft.ghealth.mybatis.searcher.EmailSearcher;
import com.todaysoft.ghealth.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmailService implements IEmailService{

    @Autowired
    private EmailMapper mapper;


    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof EmailSearcher))
        {
            throw new IllegalArgumentException();
        }

        return mapper.count((EmailSearcher)searcher);
    }

    @Override
    public List<Email> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof EmailSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            EmailSearcher tis = (EmailSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }

        return mapper.search((EmailSearcher)searcher);
    }

    @Override
    public Email get(String id)
    {
        return mapper.get(id);
    }


    @Override
    @Transactional
    public void modify(Email data)
    {
        mapper.modify(data);
    }

    @Override
    @Transactional
    public void create(Email data)
    {
        mapper.create(data);
    }

    @Override
    public List<Email> getList()
    {
        return mapper.getList();
    }
}
