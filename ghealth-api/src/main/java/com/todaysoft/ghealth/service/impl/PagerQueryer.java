package com.todaysoft.ghealth.service.impl;

import java.util.List;

import com.todaysoft.ghealth.base.response.Pager;

public class PagerQueryer<T>
{
    private PagerQueryHandler<T> handler;
    
    public PagerQueryer(PagerQueryHandler<T> handler)
    {
        this.handler = handler;
    }
    
    public Pager<T> query(Object searcher, int pageNo, int pageSize)
    {
        int count = handler.count(searcher);
        
        if (count <= 0)
        {
            return Pager.empty(pageNo, pageSize);
        }
        
        int minPageNo = 1;
        int maxPageNo = count / pageSize;
        
        if (maxPageNo == 0 || count % pageSize != 0)
        {
            maxPageNo++;
        }
        
        pageNo = pageNo < minPageNo ? minPageNo : pageNo;
        pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
        
        List<T> records = handler.query(searcher, (pageNo - 1) * pageSize, pageSize);
        return Pager.generate(pageNo, pageSize, count, records);
    }
}
