package com.todaysoft.ghealth.service.impl.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.model.TestingItem;

public class TestingItemEvaluateConfig
{
    private TestingItem testingItem;
    
    private TestingItemEvaluateReferenceValue referenceValue;
    
    private Map<String, TestingItemLocusEvaluateConfig> locusConfig;
    
    public List<TestingItemLocusEvaluateConfig> getLocusEvaluateConfigs()
    {
        if (CollectionUtils.isEmpty(locusConfig))
        {
            return Collections.emptyList();
        }
        
        return new ArrayList<TestingItemLocusEvaluateConfig>(locusConfig.values());
    }
    
    public TestingItem getTestingItem()
    {
        return testingItem;
    }
    
    public void setTestingItem(TestingItem testingItem)
    {
        this.testingItem = testingItem;
    }
    
    public TestingItemEvaluateReferenceValue getReferenceValue()
    {
        return referenceValue;
    }
    
    public void setReferenceValue(TestingItemEvaluateReferenceValue referenceValue)
    {
        this.referenceValue = referenceValue;
    }
    
    public void setLocusConfig(Map<String, TestingItemLocusEvaluateConfig> locusConfig)
    {
        this.locusConfig = locusConfig;
    }
}
