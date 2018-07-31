package com.todaysoft.ghealth.open.api.service.wrapper;

import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.open.api.mybatis.model.Customer;
import com.todaysoft.ghealth.open.api.restful.model.CustomerDTO;

@Component
public class CustomerWrapper extends Wrapper<Customer, CustomerDTO>
{
    @Override
    protected String[] getIgnoreProperties()
    {
        return new String[] {"createTime"};
    }
    
    @Override
    protected void setIgnoreProperties(Customer source, CustomerDTO target)
    {
        target.setCreateTime(format(source.getCreateTime()));
    }
}
