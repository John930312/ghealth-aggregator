package com.todaysoft.ghealth.mybatis.model;

public class Email extends PrimaryEntity
{
    private String protocol;
    
    private String serverHost;
    
    private Integer serverPort;
    
    private String username;
    
    private String password;
    
    private String fromAddress;
    
    private String fromPersonal;
    
    public String getProtocol()
    {
        return protocol;
    }
    
    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }
    
    public String getServerHost()
    {
        return serverHost;
    }
    
    public void setServerHost(String serverHost)
    {
        this.serverHost = serverHost;
    }
    
    public Integer getServerPort()
    {
        return serverPort;
    }
    
    public void setServerPort(Integer serverPort)
    {
        this.serverPort = serverPort;
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
    
    public String getFromAddress()
    {
        return fromAddress;
    }
    
    public void setFromAddress(String fromAddress)
    {
        this.fromAddress = fromAddress;
    }
    
    public String getFromPersonal()
    {
        return fromPersonal;
    }
    
    public void setFromPersonal(String fromPersonal)
    {
        this.fromPersonal = fromPersonal;
    }
}
