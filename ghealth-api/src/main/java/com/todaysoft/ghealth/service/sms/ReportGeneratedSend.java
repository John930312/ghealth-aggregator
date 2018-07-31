package com.todaysoft.ghealth.service.sms;

import com.todaysoft.ghealth.mybatis.model.ReportGenerated;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: xjw
 * @Date: 2018/7/17 8:48
 */
@Component
public class ReportGeneratedSend extends AbstractSmsSend implements SMSSend
{
    private final String AGENCY_TEMPLATE_ID = "237038";
    
    private final String CUSTOMER_TEMPLATE_ID = "231668";
    
    @Override
    public void send()
    {
        ReportGenerated reportGenerated = shortMessageCon.getReportGenerated();
        
        if (Objects.isNull(reportGenerated))
        {
            throw new SecurityException("报告发送短信功能，配置为null");
        }
        
        if (NOTIFY_ENABLED.equals(reportGenerated.getNotifyEnabled()))
        {
            sendTo(reportGenerated.getNotifyTarget(), CUSTOMER_TEMPLATE_ID, AGENCY_TEMPLATE_ID, REPORT_GENERATED_SEND);
        }
    }
}
