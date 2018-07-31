package com.todaysoft.ghealth.service.impl.core;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class AbilityEvaluator extends AbstractTestingItemEvaluator
{
    @Override
    protected Double testingItemEvaluate(TestingItemEvaluateConfig config, Map<String, TestingItemLocusEvaluateResult> locusEvaluateResults, String sex)
    {
        return 1D;
    }
}
