package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.ReportTemplateDTO;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplate;

@Component
public class ReportTemplateWrapper
{
    public List<ReportTemplate> wrap(List<ReportTemplateDTO> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        ReportTemplate template;
        List<ReportTemplate> templates = new ArrayList<ReportTemplate>();
        
        for (ReportTemplateDTO record : records)
        {
            template = new ReportTemplate();
            wrapRecord(record, template);
            templates.add(template);
        }
        
        return templates;
    }
    
    public ReportTemplate wrap(ReportTemplateDTO record)
    {
        if (null == record)
        {
            return null;
        }
        
        ReportTemplate data = new ReportTemplate();
        wrapRecord(record, data);
        return data;
    }
    
    private void wrapRecord(ReportTemplateDTO source, ReportTemplate target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
    }
}
