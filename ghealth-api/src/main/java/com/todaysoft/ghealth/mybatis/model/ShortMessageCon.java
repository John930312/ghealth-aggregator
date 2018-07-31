package com.todaysoft.ghealth.mybatis.model;

public class ShortMessageCon
{
    //签收
    private SampleSigned sampleSigned;
    
    //送检
    private SampleDelivered sampleDelivered;
    
    //报告
    private ReportGenerated reportGenerated;
    
    public SampleSigned getSampleSigned()
    {
        return sampleSigned;
    }
    
    public void setSampleSigned(SampleSigned sampleSigned)
    {
        this.sampleSigned = sampleSigned;
    }
    
    public SampleDelivered getSampleDelivered()
    {
        return sampleDelivered;
    }
    
    public void setSampleDelivered(SampleDelivered sampleDelivered)
    {
        this.sampleDelivered = sampleDelivered;
    }
    
    public ReportGenerated getReportGenerated()
    {
        return reportGenerated;
    }
    
    public void setReportGenerated(ReportGenerated reportGenerated)
    {
        this.reportGenerated = reportGenerated;
    }
}
