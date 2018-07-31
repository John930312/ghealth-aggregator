package com.todaysoft.ghealth.service.model.reportData;

import java.util.List;

public class ReportData
{
    private List<OrderData> checkupList;
    
    public List<OrderData> getCheckupList()
    {
        return checkupList;
    }
    
    public void setCheckupList(List<OrderData> checkupList)
    {
        this.checkupList = checkupList;
    }
}
