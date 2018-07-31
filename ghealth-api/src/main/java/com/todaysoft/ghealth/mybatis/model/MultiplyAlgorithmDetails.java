package com.todaysoft.ghealth.mybatis.model;

import java.math.BigDecimal;

public class MultiplyAlgorithmDetails
{
    private boolean mappingDisease;
    
    private BigDecimal maleAverageValue;
    
    private BigDecimal femaleAverageValue;
    
    public boolean isMappingDisease()
    {
        return mappingDisease;
    }
    
    public void setMappingDisease(boolean mappingDisease)
    {
        this.mappingDisease = mappingDisease;
    }
    
    public BigDecimal getMaleAverageValue()
    {
        return maleAverageValue;
    }
    
    public void setMaleAverageValue(BigDecimal maleAverageValue)
    {
        this.maleAverageValue = maleAverageValue;
    }
    
    public BigDecimal getFemaleAverageValue()
    {
        return femaleAverageValue;
    }
    
    public void setFemaleAverageValue(BigDecimal femaleAverageValue)
    {
        this.femaleAverageValue = femaleAverageValue;
    }
}
