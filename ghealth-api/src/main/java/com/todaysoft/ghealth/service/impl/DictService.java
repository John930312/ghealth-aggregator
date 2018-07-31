package com.todaysoft.ghealth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.todaysoft.ghealth.mybatis.mapper.DictMapper;
import com.todaysoft.ghealth.mybatis.model.Dict;
import com.todaysoft.ghealth.mybatis.model.DictForm;
import com.todaysoft.ghealth.mybatis.searcher.DictSearcher;
import com.todaysoft.ghealth.service.IDictService;

@Service
public class DictService implements IDictService
{
    @Autowired(required = false)
    private DictMapper mapper;
    
    private Map<String, String> cache = new HashMap<String, String>();
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof DictSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((DictSearcher)searcher);
    }
    
    @Override
    public List<Dict> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof DictSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            DictSearcher tis = (DictSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((DictSearcher)searcher);
    }
    
    @Override
    public List<Dict> findList(DictSearcher searcher)
    {
        return mapper.findList(searcher);
    }
    
    @Override
    public Dict findByCategoryAndValue(DictSearcher searcher)
    {
        return mapper.findByCategoryAndValue(searcher);
    }
    
    @Override
    public String getText(String category, String value)
    {
        if (StringUtils.isEmpty(category) || StringUtils.isEmpty(value))
        {
            return "";
        }
        
        String cacheKey = category + "-" + value;
        String text = cache.get(cacheKey);
        
        if (null == text)
        {
            DictSearcher searcher = new DictSearcher();
            searcher.setCategory(category);
            searcher.setDictValue(value);
            Dict entity = findByCategoryAndValue(searcher);
            
            if (null == entity || null == entity.getDictText())
            {
                text = "";
            }
            else
            {
                text = entity.getDictText();
            }
            
            cache.put(cacheKey, text);
        }
        
        return text;
    }
    
    @Override
    public Dict get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(DictForm data)
    {
        mapper.create(data);
    }
    
    @Override
    @Transactional
    public void modify(DictForm data)
    {
        mapper.update(data);
    }
    
    @Override
    public void deleteByCategory(String category)
    {
        mapper.deleteByCategory(category);
    }
}
