package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.mybatis.model.Case;
import com.todaysoft.ghealth.mybatis.model.CaseMatchesAlgorithmDetails;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.impl.core.*;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestingItemAlgorithmForML extends AbstractTestingItemAlgorithm
{
    @Override
    public TestingItemAlgorithmConfig getTestingItemAlgorithmConfig(TestingItem testingItem)
    {
        TestingItemAlgorithmConfig config = new TestingItemAlgorithmConfig();
        String evalAlgorithmDetails = testingItem.getEvalAlgorithmDetails();
        if (StringUtils.isEmpty(evalAlgorithmDetails))
        {
            throw new ServiceException("项目为" + testingItem.getName() + "没有配置算法");
        }
        CaseMatchesAlgorithmDetails caseMatchesAlgorithm = JsonUtils.fromJson(evalAlgorithmDetails, new TypeReference<CaseMatchesAlgorithmDetails>()
        {
        });
        if (Objects.isNull(caseMatchesAlgorithm))
        {
            throw new ServiceException("项目为" + testingItem.getName() + "json数据转对象有误");
        }
        config.setCaseMatchesAlgorithm(caseMatchesAlgorithm);
        return config;
    }
    
    @Override
    public Double calculate(TestingItemAlgorithmConfig config, String sex, Map<String, TestingItemLocusEvaluateResult> map)
    {
        CaseMatchesAlgorithmDetails caseMatchesAlgorithm = config.getCaseMatchesAlgorithm();
        
        if (Objects.isNull(caseMatchesAlgorithm))
        {
            throw new ServiceException("项目-" + config.getTestingItem().getName() + "算法配置有误！");
        }
        
        Map<String, TestingItemLocusEvaluateConfig> locusConfigMap = config.getLocusConfig();
        
        double realFactor = 0.0;
        if (!CollectionUtils.isEmpty(locusConfigMap))
        {
            List<Case> cases = caseMatchesAlgorithm.getCases();
            
            boolean flag = true;
            Map<Integer, Integer> temMap = getTemMap(locusConfigMap, map, cases);
            
            for (Case data : cases)
            {
                if (null == temMap.get(data.getMatchesValue()))
                {
                    continue;
                }
                if (data.getMatchesCount() <= temMap.get(data.getMatchesValue()))
                {
                    flag = false;
                    realFactor = data.getEvaluateValue();
                    break;
                }
            }
            if (flag)
            {
                realFactor = caseMatchesAlgorithm.getUnmatchedEvaluateValue();
            }
        }
        return realFactor;
    }
    
    @Override
    public CancerData getTuseDatas(TestingItemAlgorithmConfig testingItemAlgorithmConfig, String sex, Map<String, TestingItemLocusEvaluateResult> map)
    {
        return null;
    }
    
    private Map<Integer, Integer> getTemMap(Map<String, TestingItemLocusEvaluateConfig> locusConfigMap, Map<String, TestingItemLocusEvaluateResult> map, List<Case> cases)
    {
        Map<Integer, Integer> temMap = new HashMap<>();
        int i = 1;
        for (Map.Entry<String, TestingItemLocusEvaluateConfig> locusConfig : locusConfigMap.entrySet())
        {
            TestingItemLocusEvaluateResult result = map.get(locusConfig.getKey());
            if (null != result)
            {
                Double factor = result.getFactor();
                if (null == factor)
                {
                    throw new ServiceException("上传位点基因型  没有对应的数据！");
                }
                
                for (Case data : cases)
                {
                    if (factor == data.getMatchesValue())
                    {
                        if (temMap.containsKey(data.getMatchesValue()))
                        {
                            i++;
                        }
                        temMap.put(data.getMatchesValue(), i);
                    }
                }
            }
        }
        return temMap;
    }
}
