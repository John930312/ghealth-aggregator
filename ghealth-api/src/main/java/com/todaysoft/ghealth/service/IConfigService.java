package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.mybatis.searcher.ConfigSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IConfigService extends PagerQueryHandler<Config>
{
    void modify(Config data);
    
    Config get(String id);

    List<Config> list(ConfigSearcher searcher);
}
