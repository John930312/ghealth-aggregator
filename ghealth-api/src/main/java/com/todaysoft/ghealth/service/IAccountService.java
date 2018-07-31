package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;

public interface IAccountService
{
    AgencyAccountDetails getAgencyAccountDetails(String token);
    
    ManagementAccountDetails getManagementAccountDetails(String token);
}
