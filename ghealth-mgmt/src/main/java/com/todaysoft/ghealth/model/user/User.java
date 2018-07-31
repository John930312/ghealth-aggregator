package com.todaysoft.ghealth.model.user;

import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.model.role.Role;

public class User
{
    private String id;
    
    private String username;
    
    private String password;
    
    private Boolean enabled;
    
    private String name;
    
    private String phone;
    
    private String email;
    
    private String roleId;
    
    private String roleName;
    
    private List<Role> roles;
    
    private Date createTime;
    
    private String creatorName;
    
    private Date updateTime;
    
    private String updatorName;
    
    private Boolean deleted;
    
    private Date deleteTime;
    
    private String deletorName;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
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
    
    public Boolean getEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled)
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
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getUpdatorName()
    {
        return updatorName;
    }
    
    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }
    
    public Boolean getDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public String getDeletorName()
    {
        return deletorName;
    }
    
    public void setDeletorName(String deletorName)
    {
        this.deletorName = deletorName;
    }
    
    public String getRoleId()
    {
        return roleId;
    }
    
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }
    
    public String getRoleName()
    {
        return roleName;
    }
    
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    
    public List<Role> getRoles()
    {
        return roles;
    }
    
    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }
}