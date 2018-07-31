package com.todaysoft.ghealth.mgmt.model;

import java.util.List;

public class OrderTestingDataDTO
{
    private String orderId;
    
    private List<LocusGenetypeDTO> genetypes;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public List<LocusGenetypeDTO> getGenetypes()
    {
        return genetypes;
    }
    
    public void setGenetypes(List<LocusGenetypeDTO> genetypes)
    {
        this.genetypes = genetypes;
    }
}
