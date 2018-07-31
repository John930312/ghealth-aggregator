package com.todaysoft.ghealth.mybatis.model;

public class User extends PrimaryEntity
{
    private String username;
    
    private String password;
    
    private boolean enabled;
    
    private String name;
    
    private String phone;
    
    private String email;
    
    private boolean builtin;
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public boolean isBuiltin()
    {
        return builtin;
    }
    
    public void setBuiltin(boolean builtin)
    {
        this.builtin = builtin;
    }
}