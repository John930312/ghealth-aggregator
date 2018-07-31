package com.todaysoft.ghealth.wechat.mvc.error;

import com.todaysoft.ghealth.wechat.service.IMessageService;
import com.todaysoft.ghealth.wechat.service.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class ExceptionResolver extends SimpleMappingExceptionResolver
{
    private static Logger log = LoggerFactory.getLogger(ExceptionResolver.class);
    
    @Autowired
    private IMessageService messageService;
    
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        ModelAndView mv = new ModelAndView("/error");
        if (ex instanceof ServiceException)
        {
            ServiceException exception = (ServiceException)ex;
            mv.getModelMap().addAttribute("error_code", exception.getError());
            
            String errorMessage = null;
            if (StringUtils.isEmpty(exception.getMessage()))
            {
                errorMessage = messageService.getMessage(exception.getError());
            }
            else
            {
                errorMessage = exception.getMessage();
            }
            mv.getModelMap().addAttribute("error_message", errorMessage);
        }
        
        mv.getModelMap().addAttribute("exception", ex);
        return mv;
    }
    
    @Override
    protected void logException(Exception ex, HttpServletRequest request)
    {
        if (ex instanceof ServiceException)
        {
            log.error("Error code " + ((ServiceException)ex).getError());
        }
        else
        {
            log.error(buildLogMessage(ex, request), ex);
        }
    }
}
