package com.todaysoft.ghealth.mybatis.model;

public class UserRoleRelation
{
    
    private String userId;
    
    private String roleId;
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getRoleId()
    {
        return roleId;
    }
    
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
}
