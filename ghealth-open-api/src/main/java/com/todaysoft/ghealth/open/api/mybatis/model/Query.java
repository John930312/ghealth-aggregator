package com.todaysoft.ghealth.open.api.mybatis.model;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

public class Query
{
    private Integer offset;
    
    private Integer limit;
    
    private List<OrderbyClause> orderbys;
    
    public void format()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        
        Class<?> type;
        
        for (Field field : fields)
        {
            type = field.getType();
            
            try
            {
                Object value = field.get(this);
                
                if (String.class.equals(type))
                {
                    if (StringUtils.isEmpty(value))
                    {
                        field.set(this, null);
                    }
                }
                else if (Set.class.equals(type))
                {
                    if (null != value)
                    {
                        Set<Object> elements = new HashSet<Object>();
                        
                        for (Object element : (Set<?>)value)
                        {
                            if (!StringUtils.isEmpty(element))
                            {
                                elements.add(element);
                            }
                        }
                        
                        if (elements.isEmpty())
                        {
                            field.set(this, null);
                        }
                    }
                }
            }
            catch (IllegalArgumentException e)
            {
                //
            }
            catch (IllegalAccessException e)
            {
                //
            }
        }
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
    public List<OrderbyClause> getOrderbys()
    {
        return orderbys;
    }
    
    public void setOrderbys(List<OrderbyClause> orderbys)
    {
        this.orderbys = orderbys;
    }
}
