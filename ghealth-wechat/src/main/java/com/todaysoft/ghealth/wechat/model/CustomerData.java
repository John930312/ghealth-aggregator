package com.todaysoft.ghealth.wechat.model;

import java.util.List;

public class CustomerData
{
    private String phone;
    
    private List<Customer> customers;
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public List<Customer> getCustomers()
    {
        return customers;
    }
    
    public void setCustomers(List<Customer> customers)
    {
        this.customers = customers;
    }
}
