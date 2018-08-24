package com.todaysoft.ghealth.base.response.dto;

import com.todaysoft.ghealth.mgmt.model.LocusGenetypeDTO;

import java.util.List;

public class OrderSimpleDTO
{
    private String id;
    
    private String code;
    
    private String status;

    private String vigilance;

    private List<LocusGenetypeDTO> locusGenetypeDTOS;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public List<LocusGenetypeDTO> getLocusGenetypeDTOS()
    {
        return locusGenetypeDTOS;
    }
    
    public void setLocusGenetypeDTOS(List<LocusGenetypeDTO> locusGenetypeDTOS)
    {
        this.locusGenetypeDTOS = locusGenetypeDTOS;
    }

    public String getVigilance() {
        return vigilance;
    }

    public void setVigilance(String vigilance) {
        this.vigilance = vigilance;
    }
}
