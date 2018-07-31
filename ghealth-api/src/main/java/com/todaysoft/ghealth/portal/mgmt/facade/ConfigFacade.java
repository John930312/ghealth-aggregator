package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.base.response.ListResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.mgmt.request.MaintainConfigRequest;
import com.todaysoft.ghealth.mgmt.request.QueryConfigRequest;
import com.todaysoft.ghealth.mybatis.searcher.ConfigSearcher;
import com.todaysoft.ghealth.service.IConfigService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;

@Component
public class ConfigFacade
{
    @Autowired
    private IConfigService configService;
    
    public PagerResponse<Config> pager(QueryConfigRequest request)
    {
        
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        ConfigSearcher configSearcher = new ConfigSearcher();
        configSearcher.setName(request.getName());
        PagerQueryer<Config> queryer = new PagerQueryer<Config>(configService);
        Pager<Config> pager = queryer.query(configSearcher, pageNo, pageSize);
        Pager<Config> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), pager.getRecords());
        return new PagerResponse<Config>(result);
    }
    
    public void modify(MaintainConfigRequest request)
    {
        Config data = new Config();
        BeanUtils.copyProperties(request, data);
        configService.modify(data);
    }
    
    public ObjectResponse<Config> get(MaintainConfigRequest request)
    {
        return new ObjectResponse<Config>(configService.get(request.getId()));
    }

    public ListResponse<Config> list(QueryConfigRequest request)
    {
        ConfigSearcher searcher = new ConfigSearcher();
        BeanUtils.copyProperties(request,searcher);
        return new ListResponse<Config>(configService.list(searcher));
    }
}
