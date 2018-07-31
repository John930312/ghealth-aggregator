package com.todaysoft.ghealth.open.api.service.bean;

import java.util.List;

public class EvaluatedGradeDetails
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
