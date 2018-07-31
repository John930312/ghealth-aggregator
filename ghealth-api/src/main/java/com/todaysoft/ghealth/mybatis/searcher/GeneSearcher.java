package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Set;

public class GeneSearcher
{
    private String symbol;

    private String name;

    private boolean nameExactMatches;

    private boolean symbolExactMatches;

    private Set<String> excludeKeys;
    
    private Integer offset;
    
    private Integer limit;
    
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNameExactMatches() {
        return nameExactMatches;
    }

    public void setNameExactMatches(boolean nameExactMatches) {
        this.nameExactMatches = nameExactMatches;
    }

    public boolean isSymbolExactMatches() {
        return symbolExactMatches;
    }

    public void setSymbolExactMatches(boolean symbolExactMatches) {
        this.symbolExactMatches = symbolExactMatches;
    }

    public Set<String> getExcludeKeys() {
        return excludeKeys;
    }

    public void setExcludeKeys(Set<String> excludeKeys) {
        this.excludeKeys = excludeKeys;
    }
}
