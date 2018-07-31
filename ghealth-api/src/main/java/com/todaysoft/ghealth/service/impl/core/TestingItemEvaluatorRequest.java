package com.todaysoft.ghealth.service.impl.core;

import com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm.CancerData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Component
public class TestingItemEvaluatorRequest
{
    private static Logger log = LoggerFactory.getLogger(AbstractTestingItemEvaluator.class);
    
    public TestingItemEvaluateResult evaluate(TestingItemAlgorithmConfig config, Map<String, String> genetypes, String sex)
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
                Double value = locusEvaluateConfig.getFactor(genetype, sex);
                result.setLocusEvaluateResult(locusName, genetype, value);
            }
        });
        if ("TUSE".equals(config.getTestingItem().getCode()))
        {
            CancerData cancerData = config.getAlgorithm().getTuseDatas(config, sex, result.getLocusEvaluateResultsAsMap());
            result.setCancerData(cancerData);
        }
        else
        {
            Double value = config.getAlgorithm().calculate(config, sex, result.getLocusEvaluateResultsAsMap());
            result.setTestingItemEvaluateValue(value);
        }
        return result;
    }
    
    private TestingItemEvaluateResult buildEvaluateResult(TestingItemAlgorithmConfig config)
    {
        List<TestingItemLocusEvaluateConfig> locusEvaluateConfigs = config.getLocusEvaluateConfigs();
        
        if (CollectionUtils.isEmpty(locusEvaluateConfigs))
        {
            throw new IllegalStateException("检测项目" + config.getTestingItem().getName() + "未配置关联位点");
        }
        
        TestingItemEvaluateResult result = new TestingItemEvaluateResult();
        result.setAlgorithmConfig(config);
        
        locusEvaluateConfigs.forEach(locusEvaluateConfig -> {
            result.addLocusEvaluateResult(locusEvaluateConfig.getLocus());
        });
        
        return result;
    }
}
