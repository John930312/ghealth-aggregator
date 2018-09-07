package com.todaysoft.ghealth.open.api.mybatis.mapper;

import com.todaysoft.ghealth.open.api.mybatis.model.CustomerRequest;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper
{
    int create(CustomerRequest record);
    
    int existCustomer(@Param("phone") String phone, @Param("name") String name);
}