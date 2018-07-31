package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.todaysoft.ghealth.mybatis.model.TestingItem;

public class TestingItemAlgorithmFactory
{
    public static AbstractTestingItemAlgorithm getAlgorithm(TestingItem item)
    {
        if (TestingItem.ALGORITHM_MC.equals(item.getEvalAlgorithm()))
        {
            return new TestingItemAlgorithmForMC();
        }
        else if (TestingItem.ALGORITHM_ML.equals(item.getEvalAlgorithm()))
        {
            return new TestingItemAlgorithmForML();
        }
        else
        {
            return new TestingItemAlgorithmForTuse();
        }
    }
}
