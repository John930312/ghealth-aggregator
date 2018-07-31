package com.todaysoft.ghealth.service.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.request.UsernamePasswordLoginRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.AuthorizedAccountDetails;
import com.todaysoft.ghealth.service.impl.Gateway;
import com.todaysoft.ghealth.support.AccountDetails;
import com.todaysoft.ghealth.support.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService
{
    
    @Autowired
    private Gateway gateway;
    
    @Override
    public AccountDetails authenticate(String username, String password)
    {
        UsernamePasswordLoginRequest request = new UsernamePasswordLoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        ObjectResponse<AuthorizedAccountDetails> response =
            gateway.request("/mgmt/login", request, new ParameterizedTypeReference<ObjectResponse<AuthorizedAccountDetails>>()
            {
            });
        
        AuthorizedAccountDetails data = response.getData();
        AccountDetails account = new AccountDetails(data.getName(), data.getUsername(), data.getToken());
        account.setBuiltin(data.isBuiltin());
        account.setAuthorizedResources(data.getAuthorizedResources());
        return account;
    }
}
