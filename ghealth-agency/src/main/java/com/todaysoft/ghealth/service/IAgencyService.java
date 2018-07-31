package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.agency.Agency;

public interface IAgencyService
{
    Agency get(String id);
    
    void modify(Agency data);

    void modifyPassword(Agency data);
}
