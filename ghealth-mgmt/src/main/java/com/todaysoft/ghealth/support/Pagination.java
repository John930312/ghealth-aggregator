package com.todaysoft.ghealth.support;

import java.util.List;

public class Pagination<T>
{
    private int pageNo;
    
    private int pageSize = 20;
    
    private int totalCount;
    
    private List<T> records;
    
    public Pagination()
    {
    }
    
    public Pagination(int pageNo, int pageSize, int totalCount)
    {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
    
    public Pagination(Pagination<?> pagination)
    {
        this.pageNo = pagination.getPageNo();
        this.pageSize = pagination.getPageSize();
        this.totalCount = pagination.getTotalCount();
    }
    
    public int getTotalPage()
    {
        int pageCount = totalCount / pageSize;
        
        if (pageCount == 0 || totalCount % pageSize != 0)
        {
            pageCount++;
        }
        
        return pageCount;
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
