package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ListResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.mgmt.request.MaintainConfigRequest;
import com.todaysoft.ghealth.mgmt.request.QueryConfigRequest;
import com.todaysoft.ghealth.model.config.ConfigSearcher;
import com.todaysoft.ghealth.service.IConfigService;
import com.todaysoft.ghealth.support.Pagination;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService implements IConfigService
{
    @Autowired
    private Gateway gateway;
    
    @Override
    public Pagination<Config> pagination(ConfigSearcher searcher, int pageNo, int pageSize)
    {
        QueryConfigRequest request = new QueryConfigRequest();
        request.setName(searcher.getName());
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<Config> response = gateway.request("/mgmt/config/pager", request, new ParameterizedTypeReference<PagerResponse<Config>>()
        {
        });
        Pager<Config> pager = response.getData();
        Pagination<Config> pagination = new Pagination<Config>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        pagination.setRecords(pager.getRecords());
        return pagination;
    }
    
    @Override
    public void modify(Config data)
    {
        MaintainConfigRequest request = new MaintainConfigRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/config/modify", request);
    }
    
    @Override
    public Config get(String id)
    {
        MaintainConfigRequest request = new MaintainConfigRequest();
        request.setId(id);
        ObjectResponse<Config> response = gateway.request("/mgmt/config/get", request, new ParameterizedTypeReference<ObjectResponse<Config>>()
        {
        });
        if (null == response.getData())
        {
            return new Config();
        }
        return response.getData();
    }

    @Override
    public List<Config> list(ConfigSearcher searcher)
    {
        QueryConfigRequest request = new QueryConfigRequest();
        request.setName(searcher.getName());
        ListResponse<Config> response =
                gateway.request("/mgmt/config/list", request, new ParameterizedTypeReference<ListResponse<Config>>()
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
