package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.mgmt.request.QueryAgenciesRequest;
import com.todaysoft.ghealth.mgmt.request.QuerySignInHistoryRequest;
import com.todaysoft.ghealth.model.agency.AgencySearcher;
import com.todaysoft.ghealth.model.signInHistory.SignInHistorySearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencyDetails;

@Component
public class AgencyWrapper
{
    @Autowired
    private AgencyProductWrapper agencyProductWrapper;
    
    public List<Agency> wrap(List<com.todaysoft.ghealth.base.response.model.Agency> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Agency agency;
        List<Agency> agencies = new ArrayList<Agency>();
        
        for (com.todaysoft.ghealth.base.response.model.Agency record : records)
        {
            agency = new Agency();
            wrapRecord(record, agency);
            agencies.add(agency);
        }
        
        return agencies;
    }
    
    public AgencyDetails wrap(com.todaysoft.ghealth.base.response.model.AgencyDetails record)
    {
        if (null == record)
        {
            return null;
        }
        
        AgencyDetails details = new AgencyDetails();
        wrapDetails(record, details);
        return details;
    }

    public QueryAgenciesRequest wrapperSearcher(AgencySearcher searcher)
    {
        QueryAgenciesRequest request = new QueryAgenciesRequest();
        BeanUtils.copyProperties(searcher, request, "minAmount", "maxAmount");

        if (null != searcher.getMinAmount())
        {
            request.setMinAmount(searcher.getMinAmount());
        }

        if (null != searcher.getMaxAmount())
        {
            request.setMaxAmount(searcher.getMaxAmount());
        }

        return request;
    }

    public void wrapRecord(com.todaysoft.ghealth.base.response.model.Agency source, Agency target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
    }
    
    private void wrapDetails(com.todaysoft.ghealth.base.response.model.AgencyDetails source, AgencyDetails target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime", "agentProducts");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
        target.setAgentProducts(agencyProductWrapper.wrap(source.getAgentProducts()));
    }
}
