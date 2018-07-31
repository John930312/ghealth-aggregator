package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.reportTemplate.ReportTemplate;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplateForm;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplateSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface IReportTemplateService
{
    Pagination<ReportTemplate> pagination(ReportTemplateSearcher searcher, int pageNo, int pageSize);
    
    ReportTemplate get(String id);
    
    void create(ReportTemplateForm data);
    
    void modify(ReportTemplateForm data);
    
    void delete(String id);
}
