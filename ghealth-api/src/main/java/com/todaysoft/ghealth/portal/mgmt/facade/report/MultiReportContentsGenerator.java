package com.todaysoft.ghealth.portal.mgmt.facade.report;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class MultiReportContentsGenerator extends AbstractReportContentsGenerator
{
    private List<ReportContentsGenerator> generators;
    
    public MultiReportContentsGenerator(List<ReportContentsGenerator> generators)
    {
        this.generators = generators;
    }
    
    @Override
    public ReportContents generate(ReportGenerateContext context)
    {
        ReportContents contents = new ReportContents();
        
        if (CollectionUtils.isEmpty(generators))
        {
            return contents;
        }
        
        for (ReportContentsGenerator generator : generators)
        {
            contents.merge(generator.generate(context));
        }
        
        return contents;
    }
}
