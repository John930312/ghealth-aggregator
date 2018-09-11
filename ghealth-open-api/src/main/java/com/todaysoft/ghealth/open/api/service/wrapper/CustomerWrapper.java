package com.todaysoft.ghealth.open.api.service.wrapper;

import com.todaysoft.ghealth.open.api.mybatis.model.Customer;
import com.todaysoft.ghealth.open.api.restful.model.CustomerDTO;
import com.todaysoft.ghealth.open.api.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerWrapper extends Wrapper<Customer, CustomerDTO>
{
    @Autowired
    private IAreaService areaService;
    
    @Override
    protected String[] getIgnoreProperties()
    {
        return new String[] {"createTime"};
    }
    
    @Override
    protected void setIgnoreProperties(Customer source, CustomerDTO target)
    {
        target.setCreateTime(format(source.getCreateTime()));
        target.setProvinceText(areaService.getAreaName(source.getProvince()));
        target.setCityText(areaService.getAreaName(source.getCity()));
        target.setCountyText(areaService.getAreaName(source.getCounty()));
    }
}
