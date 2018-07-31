package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.mgmt.model.ReportGenerateTaskDTO;
import com.todaysoft.ghealth.model.order.ReportGenerateTask;
import org.springframework.util.CollectionUtils;

@Component
public class ReportGenerateTaskWrapper
{
    public List<ReportGenerateTask> wrap(List<ReportGenerateTaskDTO> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }

        ReportGenerateTask template;
        List<ReportGenerateTask> templates = new ArrayList<ReportGenerateTask>();

        for (ReportGenerateTaskDTO record : records)
        {
            templates.add(wrap(record));
        }

        return templates;
    }
    
    public ReportGenerateTask wrap(ReportGenerateTaskDTO source)
    {
        if (null == source)
        {
            return null;
        }
        
        ReportGenerateTask target = new ReportGenerateTask();
        BeanUtils.copyProperties(source, target, "finishTime");
        target.setFinishTime(null == source.getFinishTime() ? null : new Date(source.getFinishTime()));
        return target;
    }
}
