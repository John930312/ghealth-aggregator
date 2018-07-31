package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.Dict;
import com.todaysoft.ghealth.mybatis.searcher.DictSearcher;
import com.todaysoft.ghealth.service.IDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DictWrapper
{
    @Autowired
    private IDictService service;
    
    public List<Dict> wrap(List<com.todaysoft.ghealth.mybatis.model.Dict> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Dict dict;
        List<Dict> dicts = new ArrayList<Dict>();
        for (com.todaysoft.ghealth.mybatis.model.Dict record : records)
        {
            dict = new Dict();
            wrap(record, dict);
            dicts.add(dict);
        }
        
        return dicts;
    }
    
    public Dict wrap(com.todaysoft.ghealth.mybatis.model.Dict record)
    {
        Dict dict = new Dict();
        wrap(record, dict);
        return dict;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Dict source, Dict target)
    {
        String category = source.getCategory();
        DictSearcher searcher = new DictSearcher();
        searcher.setCategory(category);
        if(StringUtils.isEmpty(source.getParentId()))
        {
            List<com.todaysoft.ghealth.mybatis.model.Dict> dictList = service.findList(searcher);
            source.setDicts(dictList);
        }
        BeanUtils.copyProperties(source, target);
        
    }
    
}
