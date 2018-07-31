package com.todaysoft.ghealth.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.support.IMessageService;

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
