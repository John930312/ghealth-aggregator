package com.todaysoft.ghealth.open.api.service.wrapper;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public abstract class Wrapper<S, T>
{
    @SuppressWarnings("unchecked")
    public T wrap(S source)
    {
        if (null == source)
        {
            return null;
        }
        
        Class<T> targetClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        
        try
        {
            T target = targetClass.newInstance();
            String[] ignoreProperties = getIgnoreProperties();
            
            if (null == ignoreProperties || 0 == ignoreProperties.length)
            {
                BeanUtils.copyProperties(source, target);
            }
            else
            {
                BeanUtils.copyProperties(source, target, ignoreProperties);
                setIgnoreProperties(source, target);
            }
            
            return target;
        }
        catch (InstantiationException e)
        {
            throw new IllegalStateException();
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<T> wrap(List<S> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        T target;
        String[] ignoreProperties;
        List<T> targets = new ArrayList<T>();
        Class<T> targetClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        
        for (S source : sources)
        {
            try
            {
                target = targetClass.newInstance();
                ignoreProperties = getIgnoreProperties();
                
                if (null == ignoreProperties || 0 == ignoreProperties.length)
                {
                    BeanUtils.copyProperties(source, target);
                }
                else
                {
                    BeanUtils.copyProperties(source, target, ignoreProperties);
                    setIgnoreProperties(source, target);
                }
            }
            catch (InstantiationException e)
            {
                throw new IllegalStateException();
            }
            catch (IllegalAccessException e)
            {
                throw new IllegalStateException();
            }
            
            targets.add(target);
        }
        
        return targets;
    }
    
    protected String[] getIgnoreProperties()
    {
        return null;
    }
    
    protected void setIgnoreProperties(S source, T target)
    {
        //
    }
    
    protected String format(Date date)
    {
        return null == date ? null : DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }
}
