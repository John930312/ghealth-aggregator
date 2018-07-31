package com.todaysoft.ghealth.wechat.service.sms;

public interface ICaptchaMessager
{
    void message(String phone);

    String getCaptcha(String phone);

    String destoryCaptcha(String phone);
}
