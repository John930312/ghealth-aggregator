package com.todaysoft.ghealth.wechat.service.impl;

import java.util.Locale;

import com.todaysoft.ghealth.wechat.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;



@Service
public class MessageService implements IMessageService
{
    @Autowired
    private MessageSource messageSource;
    
    @Override
    public String getMessage(String key, Object... args)
    {
        try
        {
            return messageSource.getMessage(key, args, Locale.CHINA);
        }
        catch (NoSuchMessageException e)
        {
            return key;
        }
    }
}
