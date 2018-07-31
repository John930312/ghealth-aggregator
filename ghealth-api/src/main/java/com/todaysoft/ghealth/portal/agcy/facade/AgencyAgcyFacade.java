package com.todaysoft.ghealth.portal.agcy.facade;

import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.service.IAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.Agency;
import com.todaysoft.ghealth.mgmt.request.MaintainAgencyRequest;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.wrapper.AgencyWrapper;

import java.util.Date;


@Component
public class AgencyAgcyFacade
{
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private AgencyWrapper agencyWrapper;
    
    @Autowired
    private IAccountService accountService;
    
    public ObjectResponse<Agency> get(MaintainAgencyRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Agency agency = agencyService.get(request.getId());
        return new ObjectResponse<>(agencyWrapper.wrap(agency));
    }
    
    public void modify(MaintainAgencyRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        com.todaysoft.ghealth.mybatis.model.Agency data = new com.todaysoft.ghealth.mybatis.model.Agency();
        BeanUtils.copyProperties(request, data);
        data.setUpdateTime(new Date());
        data.setUpdatorName(account.getAccountName());
        
        AgencyAccount agencyAccount = agencyService.getPrimaryAccount(request.getId());
        if (null != agencyAccount)
        {
            if (StringUtils.isNotEmpty(request.getPrimaryAccountPassword()))
            {
                agencyAccount.setPassword(request.getPrimaryAccountPassword());
            }
            else
            {
                agencyAccount.setPassword(null);
            }
            if (StringUtils.isNotEmpty(request.getContactName()))
            {
                agencyAccount.setName(request.getContactName());
            }
            if (StringUtils.isNotEmpty(request.getContactPhone()))
            {
                agencyAccount.setPhone(request.getContactPhone());
            }
            agencyAccount.setUpdateTime(data.getUpdateTime());
            agencyAccount.setUpdatorName(data.getUpdatorName());
        }
        agencyService.modify(data, agencyAccount);
    }


    public void changePassword(MaintainAgencyRequest request) {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId())) {
            throw new IllegalStateException();
        }
        AgencyAccount agencyAccount = agencyService.getAccount(request.getId(),request.getPrimaryUsername());
        agencyAccount.setPassword(request.getPrimaryAccountPassword());
        agencyService.modifyUserPassword(agencyAccount);

    }
}
