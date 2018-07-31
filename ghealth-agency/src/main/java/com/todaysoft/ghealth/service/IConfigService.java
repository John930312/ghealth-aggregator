package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.model.config.ConfigSearcher;

import java.util.List;

public interface IConfigService
{
    List<Config> list(ConfigSearcher searcher);
}
