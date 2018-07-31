package com.todaysoft.ghealth.service.wrapper.mobileWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.base.response.model.Agency;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MobileAgencyWrapper
{
    public List<Agency> wrap(List<com.todaysoft.ghealth.mybatis.model.Agency> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public Agency wrap(com.todaysoft.ghealth.mybatis.model.Agency source)
    {
        Agency target = new Agency();
        wrap(source, target);
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Agency source, Agency target)
    {
        BeanUtils.copyProperties(source, target, "submitTime", "createTime", "updateTime", "deleteTime");
    }
}
