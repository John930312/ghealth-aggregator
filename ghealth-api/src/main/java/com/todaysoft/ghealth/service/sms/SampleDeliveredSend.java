package com.todaysoft.ghealth.service.sms;

import com.todaysoft.ghealth.mybatis.model.SampleDelivered;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: xjw
 * @Date: 2018/7/17 8:47
 */
@Component
public class SampleDeliveredSend extends AbstractSmsSend implements SMSSend
{
    private final String AGENCY_TEMPLATE_ID = "231671";
    
    private final String CUSTOMER_TEMPLATE_ID = "231666";
    
    @Override
    public void send()
    {
        SampleDelivered sampleDelivered = shortMessageCon.getSampleDelivered();
        
        if (Objects.isNull(sampleDelivered))
        {
            throw new SecurityException("样本寄送发送短信功能，配置为null");
        }
        
        if (NOTIFY_ENABLED.equals(sampleDelivered.getNotifyEnabled()))
        {
            sendTo(sampleDelivered.getNotifyTarget(), CUSTOMER_TEMPLATE_ID, AGENCY_TEMPLATE_ID, SAMPLE_DELIVERED_SEND);
        }
    }
}
