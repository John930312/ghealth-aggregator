package com.todaysoft.ghealth.open.api.service.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hsgene.restful.response.DataResponse;
import com.hsgene.restful.util.CountRecords;
import com.todaysoft.ghealth.open.api.mybatis.mapper.OrderMapper;
import com.todaysoft.ghealth.open.api.mybatis.model.Customer;
import com.todaysoft.ghealth.open.api.mybatis.model.Order;
import com.todaysoft.ghealth.open.api.mybatis.model.OrderQuery;
import com.todaysoft.ghealth.open.api.mybatis.model.TestingItemEvaluation;
import com.todaysoft.ghealth.open.api.restful.model.OrderDTO;
import com.todaysoft.ghealth.open.api.restful.model.TestingItemReportDTO;
import com.todaysoft.ghealth.open.api.restful.request.OrderQueryRequest;
import com.todaysoft.ghealth.open.api.service.IOrderService;
import com.todaysoft.ghealth.open.api.service.bean.EvaluatedGradeDetails;
import com.todaysoft.ghealth.open.api.service.bean.GradeConfig;
import com.todaysoft.ghealth.open.api.service.bean.GradeInterval;
import com.todaysoft.ghealth.open.api.service.parser.OrderQueryParser;
import com.todaysoft.ghealth.open.api.service.wrapper.OrderWrapper;

@Service
public class OrderService implements IOrderService
{
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderWrapper orderWrapper;
    
    @Autowired
    private OrderQueryParser orderQueryParser;
    
    @Override
    public DataResponse<CountRecords<OrderDTO>> list(OrderQueryRequest request)
    {
        OrderQuery query = orderQueryParser.parse(request);
        
        CountRecords<OrderDTO> data = new CountRecords<OrderDTO>();
        
        if (request.isCount())
        {
            long count = orderMapper.count(query);
            data.setCount(count);
            
            if (0 == count)
            {
                data.setRecords(Collections.emptyList());
                return new DataResponse<CountRecords<OrderDTO>>(data);
            }
            
            if (null != request.getLimit() && null != request.getOffset() && request.getOffset().intValue() >= count)
            {
                int offset;
                int limit = request.getLimit().intValue();
                int mod = (int)count % limit;
                
                if (0 == mod)
                {
                    offset = (((int)count / limit) - 1) * limit;
                }
                else
                {
                    offset = ((int)count / limit) * limit;
                }
                
                query.setOffset(offset);
            }
        }
        
        List<Order> records = orderMapper.query(query);
        data.setRecords(orderWrapper.wrap(records));
        return new DataResponse<CountRecords<OrderDTO>>(data);
    }
    
    @Override
    public DataResponse<OrderDTO> get(String id)
    {
        Order order = orderMapper.get(id);
        return new DataResponse<OrderDTO>(orderWrapper.wrap(order));
    }
    
    @Override
    public DataResponse<List<TestingItemReportDTO>> getTestingItemsReport(String id)
    {
        Order order = orderMapper.get(id);
        
        if (null == order || null == order.getCustomer() || StringUtils.isEmpty(order.getCustomer().getSex()))
        {
            throw new IllegalStateException();
        }
        
        boolean isMale = Customer.SEX_MALE.equals(order.getCustomer().getSex());
        
        List<TestingItemEvaluation> evaluations = orderMapper.getOrderTestingItemEvaluations(id);
        
        List<TestingItemReportDTO> data = new ArrayList<TestingItemReportDTO>();
        
        if (CollectionUtils.isEmpty(evaluations))
        {
            return new DataResponse<List<TestingItemReportDTO>>(data);
        }
        
        GradeConfig fiveGradesConfig;
        TestingItemReportDTO record;
        
        for (TestingItemEvaluation evaluation : evaluations)
        {
            record = new TestingItemReportDTO();
            record.setTestingItemName(evaluation.getName());
            record.setTestingItemCategory(evaluation.getCategory());
            
            fiveGradesConfig = getConfigForFiveGrades(evaluation.getGradeConfig());
            
            if (TestingItemEvaluation.CATEGORY_DISEASE.equals(evaluation.getCategory()))
            {
                Double evaluatedValue = evaluation.getEvaluatedValue();
                Double referenceValue = isMale ? evaluation.getMaleReferenceValue() : evaluation.getFemaleReferenceValue();
                record.setEvaluatedValue(null == evaluatedValue ? "-" : riskPercentFormat(evaluatedValue));
                record.setReferenceValue(null == referenceValue ? "-" : riskPercentFormat(referenceValue));
                
                if (null == evaluatedValue || null == referenceValue || 0 == referenceValue.doubleValue())
                {
                    record.setRelativeValue("-");
                }
                else
                {
                    Double relativeValue = getRelativeValue(evaluatedValue, referenceValue);
                    record.setRelativeValue(riskNumberFormat(relativeValue, 2));
                    record.setEvaluatedGrade(getGrade(relativeValue, fiveGradesConfig));
                }
            }
            else
            {
                record.setEvaluatedGrade(getGrade(evaluation.getEvaluatedValue(), fiveGradesConfig));
            }
            
            data.add(record);
        }
        
        return new DataResponse<List<TestingItemReportDTO>>(data);
    }
    
    private GradeConfig getConfigForFiveGrades(String gradeConfig)
    {
        if (StringUtils.isEmpty(gradeConfig))
        {
            return null;
        }
        
        EvaluatedGradeDetails details = JsonUtils.fromJson(gradeConfig, EvaluatedGradeDetails.class);
        
        for (GradeConfig config : details.getGrades())
        {
            if (5 == config.getGradeCount())
            {
                return config;
            }
        }
        
        return null;
    }
    
    private Integer getGrade(Double value, GradeConfig config)
    {
        if (null == value || null == config || CollectionUtils.isEmpty(config.getIntervals()))
        {
            return null;
        }
        
        for (GradeInterval interval : config.getIntervals())
        {
            if (matches(value, interval))
            {
                return interval.getGrade();
            }
        }
        
        return null;
    }
    
    private boolean matches(double value, GradeInterval interval)
    {
        if (null == interval.getMax())
        {
            return interval.getMin() <= value;
        }
        else
        {
            return interval.getMin() <= value && value < interval.getMax();
        }
    }
    
    private String riskNumberFormat(Double risk, int length)
    {
        String pattern = StringUtils.rightPad("0.", length + 2, "0");
        DecimalFormat format = new DecimalFormat(pattern);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(risk);
    }
    
    private String riskPercentFormat(Double risk)
    {
        DecimalFormat format = new DecimalFormat("0.00%");
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(risk);
    }
    
    private double getRelativeValue(double evaluateValue, double referenceValue)
    {
        String ev = riskPercentFormat(evaluateValue);
        String rv = riskPercentFormat(referenceValue);
        double evd = Double.parseDouble(ev.replaceAll("%", ""));
        double rvd = Double.parseDouble(rv.replaceAll("%", ""));
        String value = riskNumberFormat(evd / rvd, 2);
        return Double.parseDouble(value);
    }
}
