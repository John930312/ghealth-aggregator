package com.todaysoft.ghealth.service.sms;

import com.todaysoft.ghealth.mybatis.model.SampleSigned;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: xjw
 * @Date: 2018/7/17 8:47
 */
@Component
public class SampleSignedSend extends AbstractSmsSend implements SMSSend
{
    private final String AGENCY_TEMPLATE_ID = "231669";
    
    private final String CUSTOMER_TEMPLATE_ID = "231664";
    
    @Override
    public void send()
    {
        SampleSigned sampleSigned = shortMessageCon.getSampleSigned();
        
        if (Objects.isNull(sampleSigned))
        {
            throw new SecurityException("样本签收发送短信功能，配置为null");
        }
        
        if (NOTIFY_ENABLED.equals(sampleSigned.getNotifyEnabled()))
        {
            sendTo(sampleSigned.getNotifyTarget(), CUSTOMER_TEMPLATE_ID, AGENCY_TEMPLATE_ID,SAMPLE_SIGNED_SEND);
        }
    }
}
