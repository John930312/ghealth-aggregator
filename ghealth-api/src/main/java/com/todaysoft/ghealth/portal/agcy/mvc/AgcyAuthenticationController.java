package com.todaysoft.ghealth.portal.agcy.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.UsernamePasswordLoginRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.AuthorizedAccountDetails;
import com.todaysoft.ghealth.portal.agcy.facade.AgcyAccountFacade;

@RestController
@RequestMapping("/agcy")
public class AgcyAuthenticationController
{
    @Autowired
    private AgcyAccountFacade facade;
    
    @RequestMapping("/login")
    public ObjectResponse<AuthorizedAccountDetails> login(@RequestBody UsernamePasswordLoginRequest request)
    {
        return facade.login(request);
    }
}
