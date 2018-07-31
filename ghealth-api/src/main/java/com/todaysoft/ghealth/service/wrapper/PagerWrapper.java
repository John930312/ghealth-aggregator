package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.Pager;

public class PagerWrapper<S, T>
{
    private Wrapper<S, T> proxy;
    
    public PagerWrapper(Wrapper<S, T> proxy)
    {
        if (null == proxy)
        {
            throw new IllegalArgumentException();
        }
        
        this.proxy = proxy;
    }
    
    public Pager<T> wrap(Pager<S> pager)
    {
        if (null == pager)
        {
            return Pager.empty(1, 10);
        }
        
        return Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), proxy.wrap(pager.getRecords()));
    }
}
