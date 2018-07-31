package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.Dict;
import com.todaysoft.ghealth.mgmt.request.QueryDictRequest;
import com.todaysoft.ghealth.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class DictService implements IDictService
{
    @Autowired
    private Gateway gateway;
    
    @Override
    public List<Dict> getDictsByCategory(String category)
    {
        QueryDictRequest request = new QueryDictRequest();
        request.setCategory(category);
        ListResponse<Dict> response = gateway.request("/agcy/dict/getDictsByCategory", request, new ParameterizedTypeReference<ListResponse<Dict>>()
        {
        });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        return response.getData();
    }
    
    @Override
    public Dict getDictByCategoryAndValue(String category, String dictValue)
    {
        QueryDictRequest request = new QueryDictRequest();
        request.setDictValue(dictValue);
        request.setCategory(category);
        request.setLogin(false);
        ObjectResponse<Dict> response = gateway.request("/agcy/dict/getDictByCategoryAndValue", request, new ParameterizedTypeReference<ObjectResponse<Dict>>()
        {
        });
        if (null == response.getData())
        {
            return new Dict();
        }
        return response.getData();
    }
}
