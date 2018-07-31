package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.ReportTemplateDTO;
import com.todaysoft.ghealth.mybatis.model.ReportTemplate;

@Component
public class ReportTemplateWrapper
{
    public List<ReportTemplateDTO> wrap(List<ReportTemplate> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        ReportTemplateDTO template;
        List<ReportTemplateDTO> templates = new ArrayList<ReportTemplateDTO>();
        
        for (ReportTemplate record : records)
        {
            template = new ReportTemplateDTO();
            wrap(record, template);
            templates.add(template);
        }
        return templates;
    }
    
    public ReportTemplateDTO wrap(ReportTemplate record)
    {
        ReportTemplateDTO data = new ReportTemplateDTO();
        wrap(record, data);
        return data;
    }
    
    private void wrap(ReportTemplate source, ReportTemplateDTO target)
    {
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setCustomized(source.isCustomized());
        
        if (null != source.getProduct())
        {
            target.setProductId(source.getProduct().getId());
            target.setProductName(source.getProduct().getName());
        }
        
        if (null != source.getAgency())
        {
            target.setAgencyId(source.getAgency().getId());
            target.setAgencyAbbr(source.getAgency().getAbbr());
        }
        
        target.setTsdgKey(source.getTsdgKey());
        target.setCreatorName(source.getCreatorName());
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdatorName(source.getUpdatorName());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
    }
}
