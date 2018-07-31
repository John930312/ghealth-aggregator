package com.todaysoft.ghealth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Component
public class ExceptionResolver extends SimpleMappingExceptionResolver
{
    private static Logger log = LoggerFactory.getLogger(ExceptionResolver.class);
    
    @Autowired
    private IMessageService messageService;
    
    @Autowired
    private BaseModelAttributesResolver baseModelAttributesResolver;
    
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        ModelAndView mv = new ModelAndView("/error");
        baseModelAttributesResolver.resolve(request, mv.getModelMap());
        
        if (ex instanceof ServiceException)
        {
            String errorCode = ((ServiceException)ex).getErrorCode();
            mv.getModelMap().addAttribute("error_code", errorCode);
            mv.getModelMap().addAttribute("error_message", messageService.getMessage(errorCode));
        }
        
        mv.getModelMap().addAttribute("exception", ex);
        return mv;
    }
    
    @Override
    protected void logException(Exception ex, HttpServletRequest request)
    {
        if (ex instanceof ServiceException)
        {
            log.error("Error code " + ((ServiceException)ex).getErrorCode());
        }
        else
        {
            log.error(buildLogMessage(ex, request), ex);
        }
    }
}
