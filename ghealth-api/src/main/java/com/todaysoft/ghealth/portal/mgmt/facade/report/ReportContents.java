package com.todaysoft.ghealth.portal.mgmt.facade.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.todaysoft.document.generate.sdk.request.CaseBookmarkContent;
import com.todaysoft.document.generate.sdk.request.TableBookmarkContent;
import com.todaysoft.document.generate.sdk.request.TextBookmarkContent;

public class ReportContents
{
    private List<TextBookmarkContent> textBookmarkContents;
    
    private List<TableBookmarkContent> tableBookmarkContents;
    
    private List<CaseBookmarkContent> caseBookmarkContents;
    
    public void merge(ReportContents contents)
    {
        if (null == contents)
        {
            return;
        }
        
        if (!CollectionUtils.isEmpty(contents.getTextBookmarkContents()))
        {
            if (null == textBookmarkContents)
            {
                textBookmarkContents = new ArrayList<TextBookmarkContent>();
            }
            
            textBookmarkContents.addAll(contents.getTextBookmarkContents());
        }
        
        if (!CollectionUtils.isEmpty(contents.getTableBookmarkContents()))
        {
            if (null == tableBookmarkContents)
            {
                tableBookmarkContents = new ArrayList<TableBookmarkContent>();
            }
            
            tableBookmarkContents.addAll(contents.getTableBookmarkContents());
        }
        
        if (!CollectionUtils.isEmpty(contents.getCaseBookmarkContents()))
        {
            if (null == caseBookmarkContents)
            {
                caseBookmarkContents = new ArrayList<CaseBookmarkContent>();
            }
            
            caseBookmarkContents.addAll(contents.getCaseBookmarkContents());
        }
    }
    
    public List<TextBookmarkContent> getTextBookmarkContents()
    {
        return textBookmarkContents;
    }
    
    public void setTextBookmarkContents(List<TextBookmarkContent> textBookmarkContents)
    {
        this.textBookmarkContents = textBookmarkContents;
    }
    
    public List<TableBookmarkContent> getTableBookmarkContents()
    {
        return tableBookmarkContents;
    }
    
    public void setTableBookmarkContents(List<TableBookmarkContent> tableBookmarkContents)
    {
        this.tableBookmarkContents = tableBookmarkContents;
    }
    
    public List<CaseBookmarkContent> getCaseBookmarkContents()
    {
        return caseBookmarkContents;
    }
    
    public void setCaseBookmarkContents(List<CaseBookmarkContent> caseBookmarkContents)
    {
        this.caseBookmarkContents = caseBookmarkContents;
    }
}
