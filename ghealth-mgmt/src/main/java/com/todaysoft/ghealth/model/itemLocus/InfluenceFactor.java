package com.todaysoft.ghealth.model.itemLocus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfluenceFactor
{
    private String genetype;
    
    @JsonProperty("male_factor")
    private Double maleFactor;
    
    @JsonProperty("female_factor")
    private Double femaleFactor;
    
    public String getGenetype()
    {
        return genetype;
    }
    
    public void setGenetype(String genetype)
    {
        this.genetype = genetype;
    }
    
    public Double getMaleFactor()
    {
        return maleFactor;
    }
    
    public void setMaleFactor(Double maleFactor)
    {
        this.maleFactor = maleFactor;
    }
    
    public Double getFemaleFactor()
    {
        return femaleFactor;
    }
    
    public void setFemaleFactor(Double femaleFactor)
    {
        this.femaleFactor = femaleFactor;
    }
}
