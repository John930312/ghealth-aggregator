package com.todaysoft.ghealth.open.api.restful.request;

import java.util.List;

import com.hsgene.restful.request.QueryRequest;

public class TestingItemQueryRequest extends QueryRequest
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
