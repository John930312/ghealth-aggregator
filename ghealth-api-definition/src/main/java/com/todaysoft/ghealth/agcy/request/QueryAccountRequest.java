package com.todaysoft.ghealth.agcy.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

import java.util.Map;

public class QueryAccountRequest extends SignatureTokenListRequest {
    private String username;

    private String phone;

    private String name;

    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("username", username);
        fields.put("phone", phone);
        fields.put("name", name);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
