package com.todaysoft.ghealth.portal.agcy.facade;

import com.todaysoft.ghealth.agcy.request.QueryConfigRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.ConfigSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgcyConfigFacade
{
    @Autowired
    private IConfigService configService;

    @Autowired
    private IAccountService accountService;

    public ListResponse<Config> list(QueryConfigRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        ConfigSearcher searcher = new ConfigSearcher();
        BeanUtils.copyProperties(request,searcher);
        return new ListResponse<Config>(configService.list(searcher));
    }
}
