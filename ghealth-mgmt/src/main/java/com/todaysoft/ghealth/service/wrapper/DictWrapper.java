package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.dict.Dict;

@Component
public class DictWrapper
{
    public List<Dict> wrap(List<com.todaysoft.ghealth.base.response.model.Dict> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Dict dict;
        List<Dict> dicts = new ArrayList<Dict>();
        for (com.todaysoft.ghealth.base.response.model.Dict record : records)
        {
            dict = new Dict();
            wrapRecord(record, dict);
            dicts.add(dict);
        }
        return dicts;
    }
    
    public Dict wrap(com.todaysoft.ghealth.base.response.model.Dict record)
    {
        if (null == record)
        {
            return null;
        }
        
        Dict dict = new Dict();
        wrapRecord(record, dict);
        return dict;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Dict source, Dict target)
    {
        BeanUtils.copyProperties(source, target);
    }
}
