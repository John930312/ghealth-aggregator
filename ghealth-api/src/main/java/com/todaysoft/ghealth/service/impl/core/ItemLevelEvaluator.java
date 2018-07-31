package com.todaysoft.ghealth.service.impl.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.mybatis.model.EvaluateGradeDetails;
import com.todaysoft.ghealth.mybatis.model.GradeConfig;
import com.todaysoft.ghealth.mybatis.model.GradeInterval;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class ItemLevelEvaluator
{
    public static int getLevelInterval(TestingItem testingItem, Double value, int level)
    {
        int result = 0;
        String evalGradeDetails = testingItem.getEvalGradeDetails();
        if (StringUtils.isEmpty(evalGradeDetails))
        {
            throw new ServiceException("项目-" + testingItem.getName() + "没有配置评估分级!");
        }
        EvaluateGradeDetails evaluateGradeDetails = JsonUtils.fromJson(evalGradeDetails, new TypeReference<EvaluateGradeDetails>()
        {
        });
        
        if (Objects.isNull(evaluateGradeDetails))
        {
            throw new ServiceException("项目-" + testingItem.getName() + "json转评估分级对象有误!");
        }
        
        List<GradeConfig> grades = evaluateGradeDetails.getGrades();
        for (GradeConfig grade : grades)
        {
            if (level == grade.getGradeCount())
            {
                List<GradeInterval> intervals = grade.getIntervals();
                for (GradeInterval interval : intervals)
                {
                    if (null == interval.getMax())
                    {
                        if (interval.getMin() <= value)
                        {
                            result = interval.getGrade();
                        }
                    }
                    else
                    {
                        if (interval.getMin() <= value && value < interval.getMax())
                        {
                            result = interval.getGrade();
                        }
                    }
                }
            }
        }
        return result;
    }
}
