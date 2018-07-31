package com.todaysoft.ghealth.portal.mgmt.facade.report;

import java.util.List;

import com.todaysoft.document.generate.sdk.request.CaseBookmarkContent;
import com.todaysoft.document.generate.sdk.request.TableBookmarkContent;
import com.todaysoft.document.generate.sdk.request.TextBookmarkContent;

public abstract class AbstractReportContentsGenerator implements ReportContentsGenerator
{
    @Override
    public ReportContents generate(ReportGenerateContext context)
    {
        ReportContents contents = new ReportContents();
        List<TextBookmarkContent> textBookmarkContents = generateTextBookmarkContents(context);
        List<CaseBookmarkContent> caseBookmarkContents = generateCaseBookmarkContents(context);
        List<TableBookmarkContent> tableBookmarkContents = generateTableBookmarkContents(context);
        contents.setTextBookmarkContents(textBookmarkContents);
        contents.setCaseBookmarkContents(caseBookmarkContents);
        contents.setTableBookmarkContents(tableBookmarkContents);
        return contents;
    }
    
    protected List<TextBookmarkContent> generateTextBookmarkContents(ReportGenerateContext context)
    {
        // 默认实现不返回任何数据，由子类继承实现
        return null;
    }
    
    protected List<CaseBookmarkContent> generateCaseBookmarkContents(ReportGenerateContext context)
    {
        // 默认实现不返回任何数据，由子类继承实现
        return null;
    }
    
    protected List<TableBookmarkContent> generateTableBookmarkContents(ReportGenerateContext context)
    {
        // 默认实现不返回任何数据，由子类继承实现
        return null;
    }
}
