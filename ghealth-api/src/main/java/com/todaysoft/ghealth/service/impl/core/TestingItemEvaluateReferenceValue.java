package com.todaysoft.ghealth.service.impl.core;

import org.apache.commons.lang3.StringUtils;

import com.todaysoft.ghealth.mybatis.model.Customer;

public class TestingItemEvaluateReferenceValue
{
    private Double maleValue;
    
    private Double femaleValue;
    
    public Double getValue(String sex)
    {
        if (StringUtils.isEmpty(sex))
        {
            return getValueForNonsexSpecified();
        }
        
        if (Customer.SEX_MALE.equals(sex))
        {
            return getMaleValue();
        }
        else if (Customer.SEX_FEMALE.equals(sex))
        {
            return getFemaleValue();
        }
        else
        {
            return getValueForNonsexSpecified();
        }
    }
    
    private Double getValueForNonsexSpecified()
    {
        if (null != maleValue && null != femaleValue)
        {
            return (maleValue + femaleValue) / 2;
        }
        else if (null != maleValue && null == femaleValue)
        {
            return maleValue;
        }
        else if (null == maleValue && null != femaleValue)
        {
            return femaleValue;
        }
        else
        {
            return null;
        }
    }
    
    public Double getMaleValue()
    {
        return maleValue;
    }
    
    public void setMaleValue(Double maleValue)
    {
        this.maleValue = maleValue;
    }
    
    public Double getFemaleValue()
    {
        return femaleValue;
    }
    
    public void setFemaleValue(Double femaleValue)
    {
        this.femaleValue = femaleValue;
    }
}
