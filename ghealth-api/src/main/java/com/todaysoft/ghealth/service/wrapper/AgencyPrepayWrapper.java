package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.AgencyPrepay;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AgencyPrepayWrapper
{
    public List<AgencyPrepay> wrap(List<com.todaysoft.ghealth.mybatis.model.AgencyPrepay> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        AgencyPrepay AgencyPrepay;
        List<AgencyPrepay> AgencyPrepays = new ArrayList<AgencyPrepay>();
        for (com.todaysoft.ghealth.mybatis.model.AgencyPrepay record : records)
        {
            AgencyPrepay = new AgencyPrepay();
            wrap(record, AgencyPrepay);
            AgencyPrepays.add(AgencyPrepay);
        }
        
        return AgencyPrepays;
    }
    
    public AgencyPrepay wrap(com.todaysoft.ghealth.mybatis.model.AgencyPrepay record)
    {
        AgencyPrepay AgencyPrepay = new AgencyPrepay();
        wrap(record, AgencyPrepay);
        return AgencyPrepay;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.AgencyPrepay source, AgencyPrepay target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        
    }
    
}
