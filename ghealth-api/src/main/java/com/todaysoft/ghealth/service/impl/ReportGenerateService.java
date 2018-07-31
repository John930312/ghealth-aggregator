package com.todaysoft.ghealth.service.impl;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.mapper.OrderMapper;
import com.todaysoft.ghealth.mybatis.mapper.ReportGenerateTaskMapper;
import com.todaysoft.ghealth.mybatis.model.ObjectStorage;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.ReportGenerateTask;
import com.todaysoft.ghealth.portal.mgmt.facade.report.ReportGenerateContext;
import com.todaysoft.ghealth.service.IReportGenerateService;
import com.todaysoft.ghealth.service.impl.core.ReportGenerator;
import com.todaysoft.ghealth.utils.IdGen;

@Service
public class ReportGenerateService implements IReportGenerateService
{
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private ReportGenerateTaskMapper reportGenerateMapper;
    
    @Override
    @Transactional
    public String generate(ReportGenerateContext context)
    {
        String key = IdGen.uuid();
        
        Order order = context.getOrder();
        
        if (!StringUtils.isEmpty(order.getReportGenerateTaskId()))
        {
            ReportGenerateTask task = reportGenerateMapper.get(order.getReportGenerateTaskId());
            
            if (null != task)
            {
                removeStoragedObject(task.getPdfFileUrl());
                removeStoragedObject(task.getWordFileUrl());
            }
            
            reportGenerateMapper.delete(order.getReportGenerateTaskId());
        }
        
        Order args = new Order();
        args.setId(order.getId());
        args.setReportGenerateTaskId(key);
        orderMapper.modify(args);
        
        ReportGenerateTask entity = new ReportGenerateTask();
        entity.setId(key);
        entity.setStatus(ReportGenerateTask.STATUS_PROCESSING);
        entity.setCreatorName(null == context.getOperator() ? null : context.getOperator().getName());
        entity.setCreateTime(new Date());
        reportGenerateMapper.insert(entity);
        
        context.setGenerateKey(key);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                ReportGenerator generator = RootContext.getBean(ReportGenerator.class);
                generator.generate(context);
            }
        }, 500);
        
        return key;
    }
    
    private void removeStoragedObject(String id)
    {
        if (StringUtils.isEmpty(id))
        {
            return;
        }
        
        ObjectStorage storage = reportGenerateMapper.getObjectStorageRecord(id);
        
        if (null == storage)
        {
            return;
        }
    }
    
    @Override
    public ReportGenerateTask getReportGenerateTask(String reportGenerateTaskId)
    {
        return reportGenerateMapper.get(reportGenerateTaskId);
    }
}
