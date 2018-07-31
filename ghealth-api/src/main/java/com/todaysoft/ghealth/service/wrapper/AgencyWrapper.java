package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.todaysoft.ghealth.mgmt.request.QueryAgenciesRequest;
import com.todaysoft.ghealth.mybatis.searcher.AgencySearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.Agency;
import com.todaysoft.ghealth.base.response.model.AgencyDetails;
import com.todaysoft.ghealth.service.IAreaService;

@Component
public class AgencyWrapper
{
    @Autowired
    private IAreaService areaService;
    
    public List<Agency> wrap(List<com.todaysoft.ghealth.mybatis.model.Agency> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Agency agency;
        List<Agency> agencies = new ArrayList<Agency>();
        
        for (com.todaysoft.ghealth.mybatis.model.Agency record : records)
        {
            agency = new Agency();
            wrap(record, agency);
            agencies.add(agency);
        }
        
        return agencies;
    }
    
    public AgencyDetails wrap(com.todaysoft.ghealth.mybatis.model.Agency record)
    {
        AgencyDetails agency = new AgencyDetails();
        wrap(record, agency);
        return agency;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Agency source, Agency target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        
        target.setProvinceText(areaService.getDistrictName(source.getProvince()));
        target.setCityText(areaService.getDistrictName(source.getCity()));
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
    }

    public void wrapperSearcher(AgencySearcher searcher,QueryAgenciesRequest request)
    {
        BeanUtils.copyProperties( request,searcher);

        if (null != request.getMinAmount())
        {
            request.setMinAmount(searcher.getMinAmount());
        }

        if (null != request.getMaxAmount())
        {
            request.setMaxAmount(searcher.getMaxAmount());
        }
    }
}
