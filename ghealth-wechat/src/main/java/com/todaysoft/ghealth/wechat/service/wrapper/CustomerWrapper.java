package com.todaysoft.ghealth.wechat.service.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.wechat.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


@Component
public class CustomerWrapper
{
    public List<Customer> wrap(List<com.todaysoft.ghealth.base.response.model.Customer> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public Customer wrap(com.todaysoft.ghealth.base.response.model.Customer source)
    {
        Customer target = new Customer();
        Optional.ofNullable(source).ifPresent(x -> wrap(x, target));
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.base.response.model.Customer source, Customer target)
    {
        BeanUtils.copyProperties(source, target, "submitTime", "createTime", "updateTime", "deleteTime");
    }
}
