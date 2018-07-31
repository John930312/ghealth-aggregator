package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.ReportTemplate;
import com.todaysoft.ghealth.mybatis.searcher.ReportTemplateSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IReportTemplateService extends PagerQueryHandler<ReportTemplate>
{
    List<ReportTemplate> search(ReportTemplateSearcher searcher);
    
    ReportTemplate get(String id);
    
    void create(ReportTemplate data);
    
    void modify(ReportTemplate data);
    
    ReportTemplate getReportTemplate(String productId, String agencyId);
}
