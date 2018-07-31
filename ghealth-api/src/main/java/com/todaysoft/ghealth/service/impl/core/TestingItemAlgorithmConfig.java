package com.todaysoft.ghealth.service.impl.core;

import com.todaysoft.ghealth.mybatis.model.CaseMatchesAlgorithmDetails;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm.CancerData;
import com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm.TestingItemAlgorithm;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestingItemAlgorithmConfig
{
    private TestingItem testingItem;
    
    private TestingItemAlgorithm algorithm;
    
    private CaseMatchesAlgorithmDetails caseMatchesAlgorithm;
    
    private TestingItemEvaluateReferenceValue referenceValue;
    
    private Map<String, TestingItemLocusEvaluateConfig> locusConfig;
    
    private List<CancerData> cancerData;
    
    public TestingItem getTestingItem()
    {
        return testingItem;
    }
    
    public void setTestingItem(TestingItem testingItem)
    {
        this.testingItem = testingItem;
    }
    
    public TestingItemAlgorithm getAlgorithm()
    {
        return algorithm;
    }
    
    public void setAlgorithm(TestingItemAlgorithm algorithm)
    {
        this.algorithm = algorithm;
    }
    
    public CaseMatchesAlgorithmDetails getCaseMatchesAlgorithm()
    {
        return caseMatchesAlgorithm;
    }
    
    public void setCaseMatchesAlgorithm(CaseMatchesAlgorithmDetails caseMatchesAlgorithm)
    {
        this.caseMatchesAlgorithm = caseMatchesAlgorithm;
    }
    
    public TestingItemEvaluateReferenceValue getReferenceValue()
    {
        return referenceValue;
    }
    
    public void setReferenceValue(TestingItemEvaluateReferenceValue referenceValue)
    {
        this.referenceValue = referenceValue;
    }
    
    public Map<String, TestingItemLocusEvaluateConfig> getLocusConfig()
    {
        return locusConfig;
    }
    
    public void setLocusConfig(Map<String, TestingItemLocusEvaluateConfig> locusConfig)
    {
        this.locusConfig = locusConfig;
    }
    
    public List<TestingItemLocusEvaluateConfig> getLocusEvaluateConfigs()
    {
        if (CollectionUtils.isEmpty(locusConfig))
        {
            return Collections.emptyList();
        }
        
        return new ArrayList<TestingItemLocusEvaluateConfig>(locusConfig.values());
    }
    
    public List<CancerData> getCancerData()
    {
        return cancerData;
    }
    
    public void setCancerData(List<CancerData> cancerData)
    {
        this.cancerData = cancerData;
    }
}
