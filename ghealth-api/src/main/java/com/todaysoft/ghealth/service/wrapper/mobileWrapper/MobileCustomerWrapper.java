package com.todaysoft.ghealth.service.wrapper.mobileWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.base.response.model.Customer;
import com.todaysoft.ghealth.service.IDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MobileCustomerWrapper
{
    @Autowired
    private IDictService dictService;
    
    public List<Customer> wrap(List<com.todaysoft.ghealth.mybatis.model.Customer> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public Customer wrap(com.todaysoft.ghealth.mybatis.model.Customer source)
    {
        Customer target = new Customer();
        Optional.ofNullable(source).ifPresent(x -> wrap(x, target));
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Customer source, Customer target)
    {
        BeanUtils.copyProperties(source, target, "submitTime", "createTime", "updateTime", "deleteTime");
        String gender = dictService.getText("GENDER", target.getSex());
        Optional.ofNullable(gender).ifPresent(x -> target.setSexText(x));
    }
}
