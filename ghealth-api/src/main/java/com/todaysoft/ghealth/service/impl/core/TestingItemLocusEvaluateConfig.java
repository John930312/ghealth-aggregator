package com.todaysoft.ghealth.service.impl.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.model.Customer;
import com.todaysoft.ghealth.mybatis.model.InfluenceFactor;
import com.todaysoft.ghealth.mybatis.model.Locus;

public class TestingItemLocusEvaluateConfig
{
    private Locus locus;
    
    private Map<String, InfluenceFactor> factors;
    
    public Double getFactor(String geneType, String sex)
    {
        if (CollectionUtils.isEmpty(factors))
        {
            return null;
        }
        
        InfluenceFactor factor = getFactorByGeneType(geneType);
        if (null == factor)
        {
            factor = getFactorByReplacedGeneType(geneType);
        }
        if (null == factor)
        {
            return null;
        }
        if (StringUtils.isEmpty(sex))
        {
            return getFactorForNonsexSpecified(factor);
        }
        
        if (Customer.SEX_MALE.equals(sex))
        {
            return getFactorForMale(factor);
        }
        else if (Customer.SEX_FEMALE.equals(sex))
        {
            return getFactorForFemale(factor);
        }
        else
        {
            return getFactorForNonsexSpecified(factor);
        }
    }
    
    private Double getFactorForNonsexSpecified(InfluenceFactor factor)
    {
        if (null != factor.getMaleFactor() && null != factor.getFemaleFactor())
        {
            return (factor.getMaleFactor() + factor.getFemaleFactor()) / 2;
        }
        else if (null != factor.getMaleFactor() && null == factor.getFemaleFactor())
        {
            return factor.getMaleFactor();
        }
        else if (null == factor.getMaleFactor() && null != factor.getFemaleFactor())
        {
            return factor.getFemaleFactor();
        }
        else
        {
            return null;
        }
    }
    
    private Double getFactorForMale(InfluenceFactor factor)
    {
        return factor.getMaleFactor();
    }
    
    private Double getFactorForFemale(InfluenceFactor factor)
    {
        return factor.getFemaleFactor();
    }
    
    public Locus getLocus()
    {
        return locus;
    }
    
    public void setLocus(Locus locus)
    {
        this.locus = locus;
    }
    
    public void setFactors(Map<String, InfluenceFactor> factors)
    {
        this.factors = factors;
    }
    
    private InfluenceFactor getFactorByGeneType(String geneType)
    {
        InfluenceFactor factor = factors.get(geneType.toUpperCase());
        if (null == factor)
        {
            StringBuilder sb = new StringBuilder(geneType);
            String geneTypeR = sb.reverse().toString();
            factor = factors.get(geneTypeR);
        }
        return factor;
    }
    
    private InfluenceFactor getFactorByReplacedGeneType(String geneType)
    {
        List<String> inconvertibleGeneTypes = new ArrayList<>();
        inconvertibleGeneTypes.add("AT");
        inconvertibleGeneTypes.add("TA");
        inconvertibleGeneTypes.add("CG");
        inconvertibleGeneTypes.add("GC");
        if (!inconvertibleGeneTypes.contains(geneType))
        {
            String replaceGeneType = relaceChar(geneType);
            return getFactorByGeneType(replaceGeneType);
        }
        
        return null;
    }
    
    public static String relaceChar(String geneType)
    {
        char[] chars = geneType.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars)
        {
            if ('a' == c || 'A' == c)
            {
                sb.append('T');
            }
            else if ('T' == c || 't' == c)
            {
                sb.append('A');
            }
            else if ('C' == c || 'c' == c)
            {
                sb.append('G');
            }
            else if ('G' == c || 'g' == c)
            {
                sb.append('C');
            }
        }
        return sb.toString();
    }
}
