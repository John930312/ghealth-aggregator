package com.todaysoft.ghealth.service.impl.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.mybatis.model.TestingItem;

@Component
public class TestingItemEvaluatorFactory
{
    @Autowired
    private DiseaseEvaluator diseaseEvaluator;
    
    @Autowired
    private AbilityEvaluator abilityEvaluator;
    
    public TestingItemEvaluator getEvaluator(TestingItem testingItem)
    {
        if (TestingItem.CATEGORY_CHILD_RISK.equals(testingItem.getCategory()))
        {
            return abilityEvaluator;
        }
        else if (TestingItem.CATEGORY_DISEASE_RISK.equals(testingItem.getCategory()))
        {
            return diseaseEvaluator;
        }
        else
        {
            return diseaseEvaluator;
        }
    }
}
