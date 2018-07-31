package com.todaysoft.ghealth.service.impl.core;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class AbstractTestingItemEvaluator implements TestingItemEvaluator
{
    private static Logger log = LoggerFactory.getLogger(AbstractTestingItemEvaluator.class);
    
    @Override
    public TestingItemEvaluateResult evaluate(TestingItemEvaluateConfig config, Map<String, String> genetypes, String sex)
    {
        TestingItemEvaluateResult result = buildEvaluateResult(config);
        
        List<TestingItemLocusEvaluateConfig> locusEvaluateConfigs = config.getLocusEvaluateConfigs();
        
        locusEvaluateConfigs.forEach(locusEvaluateConfig -> {
            
            String locusName = locusEvaluateConfig.getLocus().getName();
            String genetype = genetypes.get(locusName);
            
            if (StringUtils.isEmpty(genetype))
            {
                log.warn("Testing genetype is empty for locus {}.", locusName);
            }
            else
            {
                Double value = this.testingItemLocusEvaluate(locusEvaluateConfig, genetype, sex);
                result.setLocusEvaluateResult(locusName, genetype, value);
            }
        });
        
        Double value = testingItemEvaluate(config, result.getLocusEvaluateResultsAsMap(), sex);
        result.setTestingItemEvaluateValue(value);
        return result;
    }
    
    private TestingItemEvaluateResult buildEvaluateResult(TestingItemEvaluateConfig config)
    {
        List<TestingItemLocusEvaluateConfig> locusEvaluateConfigs = config.getLocusEvaluateConfigs();
        
        if (CollectionUtils.isEmpty(locusEvaluateConfigs))
        {
            throw new IllegalStateException("检测项目" + config.getTestingItem().getName() + "未配置关联位点");
        }
        
        TestingItemEvaluateResult result = new TestingItemEvaluateResult();
        result.setEvaluateConfig(config);
        
        locusEvaluateConfigs.forEach(locusEvaluateConfig -> {
            result.addLocusEvaluateResult(locusEvaluateConfig.getLocus());
        });
        
        return result;
    }
    
    protected Double testingItemLocusEvaluate(TestingItemLocusEvaluateConfig config, String genetype, String sex)
    {
        return config.getFactor(genetype, sex);
    }
    
    protected abstract Double testingItemEvaluate(TestingItemEvaluateConfig config, Map<String, TestingItemLocusEvaluateResult> locusEvaluateResults, String sex);
}
