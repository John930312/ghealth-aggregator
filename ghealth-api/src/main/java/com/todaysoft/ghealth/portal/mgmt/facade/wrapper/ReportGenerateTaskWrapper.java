package com.todaysoft.ghealth.portal.mgmt.facade.wrapper;

import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.mgmt.model.ReportGenerateTaskDTO;
import com.todaysoft.ghealth.mybatis.model.ReportGenerateTask;

@Component
public class ReportGenerateTaskWrapper
{
    public ReportGenerateTaskDTO wrap(ReportGenerateTask source)
    {
        if (null == source)
        {
            return null;
        }
        
        ReportGenerateTaskDTO target = new ReportGenerateTaskDTO();
        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setFinishTime(null == source.getFinishTime() ? null : source.getFinishTime().getTime());
        target.setErrorCode(source.getErrorCode());
        target.setErrorMessage(source.getErrorMessage());
        target.setPdfFileUri(source.getPdfFileUrl());
        target.setWordFileUri(source.getWordFileUrl());
        return target;
    }
}
