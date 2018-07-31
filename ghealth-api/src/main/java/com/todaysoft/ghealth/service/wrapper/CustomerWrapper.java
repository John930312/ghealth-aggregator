package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.Customer;
import com.todaysoft.ghealth.service.IAreaService;
import com.todaysoft.ghealth.service.IDictService;

@Component
public class CustomerWrapper
{
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IDictService dictService;
    
    public List<Customer> wrap(List<com.todaysoft.ghealth.mybatis.model.Customer> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Customer customer;
        List<Customer> customers = new ArrayList<Customer>();
        
        for (com.todaysoft.ghealth.mybatis.model.Customer record : records)
        {
            customer = new Customer();
            wrap(record, customer);
            customers.add(customer);
        }
        
        return customers;
    }
    
    public Customer wrap(com.todaysoft.ghealth.mybatis.model.Customer record)
    {
        Customer customer = new Customer();
        wrap(record, customer);
        return customer;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Customer source, Customer target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setSexText(dictService.getText("GENDER", source.getSex()));
        target.setProvinceText(areaService.getDistrictName(source.getProvince()));
        target.setCityText(areaService.getDistrictName(source.getCity()));
        target.setCountyText(areaService.getDistrictName(source.getCounty()));
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
    }
}
