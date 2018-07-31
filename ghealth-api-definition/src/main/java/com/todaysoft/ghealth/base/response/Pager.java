package com.todaysoft.ghealth.base.response;

import java.util.Collections;
import java.util.List;

public class Pager<T>
{
    private int pageNo;
    
    private int pageSize;
    
    private int totalCount;
    
    private List<T> records;
    
    public static <T> Pager<T> empty(Integer pageNo, Integer pageSize)
    {
        Pager<T> pager = new Pager<T>();
        pager.setPageNo(null == pageNo ? null : 1);
        pager.setPageSize(pageSize);
        pager.setTotalCount(0);
        pager.setRecords(Collections.emptyList());
        return pager;
    }
    
    public static <T> Pager<T> generate(Integer pageNo, Integer pageSize, int totalCount, List<T> records)
    {
        if (null != pageNo && null != pageSize)
        {
            int minPageNo = 1;
            int maxPageNo = totalCount / pageSize;
            
            if (maxPageNo == 0 || totalCount % pageSize != 0)
            {
                maxPageNo++;
            }
            
            pageNo = pageNo < minPageNo ? minPageNo : pageNo;
            pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
        }
        
        Pager<T> pager = new Pager<T>();
        pager.setPageNo(pageNo);
        pager.setPageSize(pageSize);
        pager.setTotalCount(totalCount);
        pager.setRecords(records);
        return pager;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public List<T> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<T> records)
    {
        this.records = records;
    }
}
