package com.todaysoft.ghealth.service.impl.core;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DiseaseEvaluator extends AbstractTestingItemEvaluator
{
    @Override
    protected Double testingItemEvaluate(TestingItemEvaluateConfig config, Map<String, TestingItemLocusEvaluateResult> locusEvaluateResults, String sex)
    {
        TestingItemEvaluateReferenceValue referenceValue = config.getReferenceValue();
        
        if (null == referenceValue)
        {
            throw new IllegalStateException("检测项目" + config.getTestingItem().getName() + "未设定平均风险");
        }
        
        Double value = referenceValue.getValue(sex);
        
        if (null == value)
        {
            throw new IllegalStateException("检测项目" + config.getTestingItem().getName() + "未设定平均风险");
        }
        
        if (value <= 0)
        {
            throw new IllegalStateException("检测项目" + config.getTestingItem().getName() + "平均风险值无效");
        }
        
        // 忽略未测出结果的值
        Set<Double> locusValues = new HashSet<Double>();
        
        locusEvaluateResults.values().forEach(result -> {
            if (null != result.getFactor())
            {
                locusValues.add(result.getFactor());
            }
        });
        
        if (CollectionUtils.isEmpty(locusValues))
        {
            return null;
        }
        
        Double total = 1D;
        
        for (Double locusValue : locusValues)
        {
            total = total * locusValue;
        }
        
        Double base = 1D;
        
        for (int i = 1; i < locusValues.size(); i++)
        {
            base = base * value;
        }
        
        return total / base;
    }
}