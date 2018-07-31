package com.todaysoft.ghealth.mybatis.model;

import java.util.List;

public class EvaluateGradeDetails
{
    private List<GradeConfig> grades;
    
    public List<GradeConfig> getGrades()
    {
        return grades;
    }
    
    public void setGrades(List<GradeConfig> grades)
    {
        this.grades = grades;
    }
    
}
