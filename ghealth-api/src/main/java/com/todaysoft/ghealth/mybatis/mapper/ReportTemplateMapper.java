package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.ReportTemplate;
import com.todaysoft.ghealth.mybatis.searcher.ReportTemplateSearcher;

public interface ReportTemplateMapper
{
    int count(ReportTemplateSearcher searcher);
    
    List<ReportTemplate> search(ReportTemplateSearcher searcher);
    
    ReportTemplate get(String id);
    
    void create(ReportTemplate record);
    
    void modify(ReportTemplate record);
}