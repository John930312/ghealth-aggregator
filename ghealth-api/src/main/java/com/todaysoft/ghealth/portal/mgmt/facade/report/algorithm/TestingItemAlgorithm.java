package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.service.impl.core.TestingItemAlgorithmConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateResult;
import com.todaysoft.ghealth.service.impl.core.TestingItemLocusEvaluateResult;

import java.util.Map;

public interface TestingItemAlgorithm
{
    TestingItemAlgorithmConfig getTestingItemAlgorithmConfig(TestingItem testingItem);
    
    Double calculate(TestingItemAlgorithmConfig testingItemAlgorithmConfig, String sex, Map<String, TestingItemLocusEvaluateResult> map);
    
    CancerData getTuseDatas(TestingItemAlgorithmConfig testingItemAlgorithmConfig, String sex, TestingItemEvaluateResult result);
}
