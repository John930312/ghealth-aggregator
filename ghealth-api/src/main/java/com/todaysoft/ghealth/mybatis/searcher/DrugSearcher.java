package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Set;

public class DrugSearcher
{
    
    private String ingredientCn;
    
    private String ingredientEn;
    
    private String geneName;
    
    private boolean ingredientCnExactMatches;
    
    private Set<String> excludeKeys;
    
    private Integer offset;
    
    private Integer limit;
    
    private String category;
    
    private Integer isAdultUsed;
    
    private Integer isChildrenUsed;
    
    public String getIngredientCn()
    {
        return ingredientCn;
    }
    
    public void setIngredientCn(String ingredientCn)
    {
        this.ingredientCn = ingredientCn;
    }
    
    public String getIngredientEn()
    {
        return ingredientEn;
    }
    
    public void setIngredientEn(String ingredientEn)
    {
        this.ingredientEn = ingredientEn;
    }
    
    public String getGeneName()
    {
        return geneName;
    }
    
    public void setGeneName(String geneName)
    {
        this.geneName = geneName;
    }
    
    public boolean isIngredientCnExactMatches()
    {
        return ingredientCnExactMatches;
    }
    
    public void setIngredientCnExactMatches(boolean ingredientCnExactMatches)
    {
        this.ingredientCnExactMatches = ingredientCnExactMatches;
    }
    
    public Set<String> getExcludeKeys()
    {
        return excludeKeys;
    }
    
    public void setExcludeKeys(Set<String> excludeKeys)
    {
        this.excludeKeys = excludeKeys;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public Integer getIsChildrenUsed()
    {
        return isChildrenUsed;
    }
    
    public void setIsChildrenUsed(Integer isChildrenUsed)
    {
        this.isChildrenUsed = isChildrenUsed;
    }
    
    public Integer getIsAdultUsed()
    {
        return isAdultUsed;
    }
    
    public void setIsAdultUsed(Integer isAdultUsed)
    {
        this.isAdultUsed = isAdultUsed;
    }
}
