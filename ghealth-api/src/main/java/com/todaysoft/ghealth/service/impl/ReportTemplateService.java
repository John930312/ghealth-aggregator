package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.mapper.ReportTemplateMapper;
import com.todaysoft.ghealth.mybatis.model.ReportTemplate;
import com.todaysoft.ghealth.mybatis.searcher.ReportTemplateSearcher;
import com.todaysoft.ghealth.service.IReportTemplateService;

@Service
public class ReportTemplateService implements IReportTemplateService
{
    @Autowired
    private ReportTemplateMapper mapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof ReportTemplateSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((ReportTemplateSearcher)searcher);
    }
    
    @Override
    public List<ReportTemplate> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof ReportTemplateSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            ReportTemplateSearcher tis = (ReportTemplateSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((ReportTemplateSearcher)searcher);
    }
    
    @Override
    public List<ReportTemplate> search(ReportTemplateSearcher searcher)
    {
        return mapper.search(searcher);
    }
    
    @Override
    public ReportTemplate get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(ReportTemplate data)
    {
        mapper.create(data);
    }
    
    @Override
    @Transactional
    public void modify(ReportTemplate data)
    {
        mapper.modify(data);
    }
    
    @Override
    public ReportTemplate getReportTemplate(String productId, String agencyId)
    {
        if (StringUtils.isEmpty(productId))
        {
            throw new IllegalArgumentException();
        }
        
        if (StringUtils.isEmpty(agencyId))
        {
            ReportTemplateSearcher searcher = new ReportTemplateSearcher();
            searcher.setProductId(productId);
            List<ReportTemplate> records = search(searcher);
            return CollectionUtils.isEmpty(records) ? null : records.get(0);
        }
        
        ReportTemplateSearcher searcher = new ReportTemplateSearcher();
        searcher.setAgencyId(agencyId);
        searcher.setProductId(productId);
        List<ReportTemplate> records = search(searcher);
        
        if (!CollectionUtils.isEmpty(records))
        {
            return records.get(0);
        }
        
        searcher = new ReportTemplateSearcher();
        searcher.setProductId(productId);
        searcher.setCustomized(false);
        records = search(searcher);
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
}
