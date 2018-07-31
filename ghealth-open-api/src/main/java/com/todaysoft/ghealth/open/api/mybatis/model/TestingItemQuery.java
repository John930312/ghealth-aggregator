package com.todaysoft.ghealth.open.api.mybatis.model;

import java.util.List;

public class TestingItemQuery extends Query
{
    private String name;

    private List<String> categoryIncludes;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<String> getCategoryIncludes()
    {
        return categoryIncludes;
    }

    public void setCategoryIncludes(List<String> categoryIncludes)
    {
        this.categoryIncludes = categoryIncludes;
    }
}
