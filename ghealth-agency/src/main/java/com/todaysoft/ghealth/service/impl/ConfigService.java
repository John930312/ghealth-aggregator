package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.QueryConfigRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.model.config.ConfigSearcher;
import com.todaysoft.ghealth.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService implements IConfigService
{
    @Autowired
    private Gateway gateway;

    @Override
    public List<Config> list(ConfigSearcher searcher)
    {
        QueryConfigRequest request = new QueryConfigRequest();
        request.setName(searcher.getName());
        ListResponse<Config> response =
                gateway.request("/agcy/config/list", request, new ParameterizedTypeReference<ListResponse<Config>>()
                {
                });
        List<Config> configs = new ArrayList<Config>();
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return configs;
        }
        configs.addAll(response.getData());
        return configs;
    }
}
