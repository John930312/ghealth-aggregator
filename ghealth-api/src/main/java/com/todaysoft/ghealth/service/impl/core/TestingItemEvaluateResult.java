package com.todaysoft.ghealth.service.impl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.todaysoft.ghealth.mybatis.model.Locus;
import com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm.CancerData;

public class TestingItemEvaluateResult
{
    private TestingItemEvaluateConfig evaluateConfig;
    
    private TestingItemAlgorithmConfig algorithmConfig;
    
    private Double evaluateValue;
    
    private Map<String, TestingItemLocusEvaluateResult> locusEvaluateResults = new HashMap<String, TestingItemLocusEvaluateResult>();
    
    private CancerData cancerData;
    
    public TestingItemEvaluateConfig getEvaluateConfig()
    {
        return evaluateConfig;
    }
    
    public void setEvaluateConfig(TestingItemEvaluateConfig evaluateConfig)
    {
        this.evaluateConfig = evaluateConfig;
    }
    
    public void setTestingItemEvaluateValue(Double value)
    {
        this.evaluateValue = value;
    }
    
    public Double getTestingItemEvaluateValue()
    {
        return evaluateValue;
    }
    
    public List<TestingItemLocusEvaluateResult> getlocusEvaluateResults()
    {
        return new ArrayList<TestingItemLocusEvaluateResult>(locusEvaluateResults.values());
    }
    
    public Map<String, TestingItemLocusEvaluateResult> getLocusEvaluateResultsAsMap()
    {
        return locusEvaluateResults;
    }
    
    public void setLocusEvaluateResult(String locusName, String genetype, Double factor)
    {
        TestingItemLocusEvaluateResult locusEvaluateResult = locusEvaluateResults.get(locusName);
        
        if (null == locusEvaluateResult)
        {
            return;
        }
        
        locusEvaluateResult.setGenetype(genetype);
        locusEvaluateResult.setFactor(factor);
    }
    
    public void addLocusEvaluateResult(Locus locus)
    {
        TestingItemLocusEvaluateResult locusEvaluateResult = new TestingItemLocusEvaluateResult();
        locusEvaluateResult.setLocus(locus);
        locusEvaluateResults.put(locus.getName(), locusEvaluateResult);
    }
    
    public TestingItemAlgorithmConfig getAlgorithmConfig()
    {
        return algorithmConfig;
    }
    
    public void setAlgorithmConfig(TestingItemAlgorithmConfig algorithmConfig)
    {
        this.algorithmConfig = algorithmConfig;
    }
    
    public CancerData getCancerData()
    {
        return cancerData;
    }
    
    public void setCancerData(CancerData cancerData)
    {
        this.cancerData = cancerData;
    }
}
