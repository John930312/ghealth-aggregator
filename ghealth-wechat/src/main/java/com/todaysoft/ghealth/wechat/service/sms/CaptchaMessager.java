package com.todaysoft.ghealth.wechat.service.sms;

import com.cloopen.rest.sdk.CCPRestSDK;
import org.springframework.stereotype.Component;

@Component
public class CaptchaMessager extends AbstractCaptchaMessager
{
    private final String templateId = "248882";
    
    @Override
    protected void message(String phone, String captcha)
    {
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("8a216da85c62c9ad015caaaa6f0419eb", "e03254f4da9f4ff0945b65eabddefb56");
        restAPI.setAppId("8a216da85c62c9ad015caaaa705919f2");
        restAPI.sendTemplateSMS(phone, templateId, new String[] {captcha});
    }
}
