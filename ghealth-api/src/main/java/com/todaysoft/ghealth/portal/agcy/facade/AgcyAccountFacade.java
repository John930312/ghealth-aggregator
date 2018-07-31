package com.todaysoft.ghealth.portal.agcy.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.request.UsernamePasswordLoginRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.AuthorizedAccountDetails;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.model.AgencyAccountLoginDetails;

@Component
public class AgcyAccountFacade
{
    @Autowired
    private IAgencyService service;
    
    public ObjectResponse<AuthorizedAccountDetails> login(UsernamePasswordLoginRequest request)
    {
        AgencyAccountLoginDetails details = service.login(request.getUsername(), request.getPassword());
        AuthorizedAccountDetails response = new AuthorizedAccountDetails();
        response.setToken(details.getToken());
        response.setName(details.getAccount().getName());
        response.setUsername(details.getAccount().getUsername());
        response.setAgencyId(details.getAccount().getAgencyId());
        response.setBuiltin(details.getAccount().isPrimaryAccount());
        response.setAuthorizedResources(details.getAuthorizedResources());
        return new ObjectResponse<AuthorizedAccountDetails>(response);
    }
}
