package com.todaysoft.ghealth.portal.mgmt.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.request.UsernamePasswordLoginRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.AuthorizedAccountDetails;
import com.todaysoft.ghealth.service.IUserService;
import com.todaysoft.ghealth.service.model.UserAccountLoginDetails;

@Component
public class MgmtAccountFacade
{
    @Autowired
    private IUserService service;
    
    public ObjectResponse<AuthorizedAccountDetails> login(UsernamePasswordLoginRequest request)
    {
        UserAccountLoginDetails details = service.login(request.getUsername(), request.getPassword());
        AuthorizedAccountDetails response = new AuthorizedAccountDetails();
        response.setToken(details.getToken());
        response.setName(details.getUser().getName());
        response.setUsername(details.getUser().getUsername());
        response.setBuiltin(details.getUser().isBuiltin());
        response.setAuthorizedResources(details.getAuthorizedResources());
        return new ObjectResponse<AuthorizedAccountDetails>(response);
    }
}
