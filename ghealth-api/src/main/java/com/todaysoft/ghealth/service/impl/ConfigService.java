package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.mybatis.mapper.ConfigMapper;
import com.todaysoft.ghealth.mybatis.searcher.ConfigSearcher;
import com.todaysoft.ghealth.service.IConfigService;

@Service
public class ConfigService implements IConfigService
{
    @Autowired
    private ConfigMapper mapper;
    
    @Override
    public Config get(String id)
    {
        return mapper.get(id);
    }

    @Override
    public List<Config> list(ConfigSearcher searcher)
    {
        return mapper.search(searcher);
    }

    @Override
    @Transactional
    public void modify(Config data)
    {
        mapper.modify(data);
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof ConfigSearcher))
        {
            throw new IllegalArgumentException();
        }
        return mapper.count((ConfigSearcher)searcher);
    }
    
    @Override
    public List<Config> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof ConfigSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            ConfigSearcher tis = (ConfigSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        return mapper.search((ConfigSearcher)searcher);
    }
}
