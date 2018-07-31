package com.todaysoft.ghealth.open.api.service.bean;

import java.util.List;

public class GradeConfig
{
    private int gradeCount;
    
    private List<GradeInterval> intervals;
    
    public int getGradeCount()
    {
        return gradeCount;
    }
    
    public void setGradeCount(int gradeCount)
    {
        this.gradeCount = gradeCount;
    }
    
    public List<GradeInterval> getIntervals()
    {
        return intervals;
    }
    
    public void setIntervals(List<GradeInterval> intervals)
    {
        this.intervals = intervals;
    }
}
