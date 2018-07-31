package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.ReportGenerateTask;
import com.todaysoft.ghealth.portal.mgmt.facade.report.ReportGenerateContext;

public interface IReportGenerateService
{
    String generate(ReportGenerateContext context);
    
    ReportGenerateTask getReportGenerateTask(String reportGenerateTaskId);
}
