package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.model.*;
import com.todaysoft.ghealth.service.ICancerService;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.impl.core.TestingItemAlgorithmConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateReferenceValue;
import com.todaysoft.ghealth.service.impl.core.TestingItemLocusEvaluateResult;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class TestingItemAlgorithmForMC extends AbstractTestingItemAlgorithm
{
    private ICancerService cancerService = RootContext.getBean(ICancerService.class);
    
    @Override
    public TestingItemAlgorithmConfig getTestingItemAlgorithmConfig(TestingItem testingItem)
    {
        TestingItemAlgorithmConfig config = new TestingItemAlgorithmConfig();
        String evalAlgorithmDetails = testingItem.getEvalAlgorithmDetails();
        if (StringUtils.isEmpty(evalAlgorithmDetails))
        {
            throw new ServiceException("项目为" + testingItem.getName() + "没有配置算法");
        }
        MultiplyAlgorithmDetails multiplyAlgorithmDetails = JsonUtils.fromJson(evalAlgorithmDetails, new TypeReference<MultiplyAlgorithmDetails>()
        {
        });
        if (Objects.isNull(multiplyAlgorithmDetails))
        {
            throw new ServiceException("项目为" + testingItem.getName() + "json数据转对象有误");
        }
        double femaleFactor;
        double maleFactor;
        if (multiplyAlgorithmDetails.isMappingDisease())
        {
            Cancer cancer = cancerService.get(testingItem.getCategoryMapping());
            if (Objects.isNull(cancer))
            {
                throw new ServiceException("项目" + testingItem.getName() + "没有关联疾病风险！");
            }
            femaleFactor = cancer.getRiskfemale();
            maleFactor = cancer.getRiskmale();
        }
        else
        {
            femaleFactor = multiplyAlgorithmDetails.getFemaleAverageValue().doubleValue();
            maleFactor = multiplyAlgorithmDetails.getMaleAverageValue().doubleValue();
        }
        TestingItemEvaluateReferenceValue referenceValue = new TestingItemEvaluateReferenceValue();
        referenceValue.setFemaleValue(femaleFactor);
        referenceValue.setMaleValue(maleFactor);
        config.setReferenceValue(referenceValue);
        return config;
    }
    
    @Override
    public Double calculate(TestingItemAlgorithmConfig config, String sex, Map<String, TestingItemLocusEvaluateResult> map)
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
        List<Double> locusValues = new ArrayList<Double>();
        
        map.values().forEach(result -> {
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
    
    @Override
    public CancerData getTuseDatas(TestingItemAlgorithmConfig testingItemAlgorithmConfig, String sex, Map<String, TestingItemLocusEvaluateResult> map)
    {
        return null;
    }
    
}