package com.todaysoft.ghealth.wechat.mvc.base;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

public class ModelResolver
{
    private static Logger log = LoggerFactory.getLogger(ModelResolver.class);
    
    private Object object;
    
    private ModelMap model;
    
    public ModelResolver(Object object, ModelMap model)
    {
        if (null == model)
        {
            throw new IllegalArgumentException();
        }
        
        this.object = object;
        this.model = model;
    }
    
    public void resolve()
    {
        if (null == object)
        {
            return;
        }
        
        Field[] fields = this.object.getClass().getDeclaredFields();
        
        for (Field field : fields)
        {
            try
            {
                field.setAccessible(true);
                this.model.addAttribute(field.getName(), field.get(this.object));
            }
            catch (IllegalArgumentException e)
            {
                log.error(e.getMessage(), e);
            }
            catch (IllegalAccessException e)
            {
                log.error(e.getMessage(), e);
            }
        }
    }
}
