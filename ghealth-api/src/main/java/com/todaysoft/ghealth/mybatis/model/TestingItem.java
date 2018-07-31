package com.todaysoft.ghealth.mybatis.model;

public class TestingItem extends PrimaryEntity
{
    public static final String CATEGORY_DISEASE_RISK = "1";
    
    public static final String CATEGORY_YJ_RISK = "2";
    
    public static final String CATEGORY_CHILD_RISK = "3";
    
    public static final String CATEGORY_PHARMACY_RISK = "4";
    
    public static final String ALGORITHM_MC = "1";
    
    public static final String ALGORITHM_ML = "2";
    
    public static final String JJDX = "ALSM2";
    
    private String code;
    
    private String name;
    
    private String category;
    
    private String categoryMapping;
    
    private String sexRestraint;
    
    private boolean enabled;
    
    private String evalAlgorithm;
    
    private String evalAlgorithmDetails;
    
    private String evalGradeDetails;
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getCategoryMapping()
    {
        return categoryMapping;
    }
    
    public void setCategoryMapping(String categoryMapping)
    {
        this.categoryMapping = categoryMapping;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSexRestraint()
    {
        return sexRestraint;
    }
    
    public void setSexRestraint(String sexRestraint)
    {
        this.sexRestraint = sexRestraint;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public String getEvalAlgorithm()
    {
        return evalAlgorithm;
    }
    
    public void setEvalAlgorithm(String evalAlgorithm)
    {
        this.evalAlgorithm = evalAlgorithm;
    }
    
    public String getEvalAlgorithmDetails()
    {
        return evalAlgorithmDetails;
    }
    
    public void setEvalAlgorithmDetails(String evalAlgorithmDetails)
    {
        this.evalAlgorithmDetails = evalAlgorithmDetails;
    }
    
    public String getEvalGradeDetails()
    {
        return evalGradeDetails;
    }
    
    public void setEvalGradeDetails(String evalGradeDetails)
    {
        this.evalGradeDetails = evalGradeDetails;
    }
}
