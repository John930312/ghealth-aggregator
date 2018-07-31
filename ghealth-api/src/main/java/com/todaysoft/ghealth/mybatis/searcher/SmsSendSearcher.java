package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Date;

/**
 * @Author: xjw
 * @Date: 2018/7/18 10:00
 */
public class SmsSendSearcher
{
    private Date before;
    
    private Date after;
    
    public Date getBefore()
    {
        return before;
    }
    
    public void setBefore(Date before)
    {
        this.before = before;
    }
    
    public Date getAfter()
    {
        return after;
    }
    
    public void setAfter(Date after)
    {
        this.after = after;
    }
}
