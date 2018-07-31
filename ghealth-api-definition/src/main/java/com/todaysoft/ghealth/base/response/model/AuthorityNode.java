package com.todaysoft.ghealth.base.response.model;

import java.util.List;

public class AuthorityNode
{
    private String id;
    
    private String name;
    
    private AuthorityNode parent;
    
    private List<AuthorityNode> child;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public AuthorityNode getParent()
    {
        return parent;
    }
    
    public void setParent(AuthorityNode parent)
    {
        this.parent = parent;
    }
    
    public List<AuthorityNode> getChild()
    {
        return child;
    }
    
    public void setChild(List<AuthorityNode> child)
    {
        this.child = child;
    }
}
