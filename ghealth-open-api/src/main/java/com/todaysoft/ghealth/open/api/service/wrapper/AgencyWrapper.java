package com.todaysoft.ghealth.open.api.service.wrapper;

import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.open.api.mybatis.model.Agency;
import com.todaysoft.ghealth.open.api.restful.model.AgencyDTO;

@Component
public class AgencyWrapper extends Wrapper<Agency, AgencyDTO>
{
    @Override
    protected String[] getIgnoreProperties()
    {
        return new String[] {"createTime"};
    }
    
    @Override
    protected void setIgnoreProperties(Agency source, AgencyDTO target)
    {
        target.setCreateTime(format(source.getCreateTime()));
    }
}
