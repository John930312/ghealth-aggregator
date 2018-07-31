package com.todaysoft.ghealth.wechat.service.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.RandomStringGenerator;

public abstract class AbstractCaptchaMessager implements ICaptchaMessager
{
    private Map<String, String> captchas = new HashMap<String, String>();
    
    @Override
    public void message(String phone)
    {
        String captcha = generateCaptcha();
        message(phone, captcha);
        captchas.put(phone, captcha);
    }
    
    @Override
    public String getCaptcha(String phone)
    {
        return captchas.get(phone);
    }
    
    @Override
    public String destoryCaptcha(String phone)
    {
        return captchas.remove(phone);
    }
    
    protected abstract void message(String phone, String captcha);
    
    protected String generateCaptcha()
    {
        return new RandomStringGenerator.Builder().withinRange('0', '9').build().generate(4);
    }
    
    public Map<String, String> getCaptchaMap()
    {
        return captchas;
    }
}
