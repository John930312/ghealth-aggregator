package com.todaysoft.ghealth.open.api.mybatis.model;

public class OrderbyClause
{
    private String field;
    
    private boolean asc;
    
    public String getField()
    {
        return field;
    }
    
    public void setField(String field)
    {
        this.field = field;
    }
    
    public boolean isAsc()
    {
        return asc;
    }
    
    public void setAsc(boolean asc)
    {
        this.asc = asc;
    }
}
