package com.todaysoft.ghealth.service.wrapper.mobileWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.base.response.model.ProductDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MobileProductDetailsWrapper
{
    public List<ProductDetails> wrap(List<com.todaysoft.ghealth.mybatis.model.Product> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public ProductDetails wrap(com.todaysoft.ghealth.mybatis.model.Product source)
    {
        ProductDetails target = new ProductDetails();
        wrap(source, target);
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Product source, ProductDetails target)
    {
        BeanUtils.copyProperties(source, target, "submitTime", "createTime", "updateTime", "deleteTime");
    }
}
