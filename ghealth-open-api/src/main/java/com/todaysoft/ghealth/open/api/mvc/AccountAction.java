package com.todaysoft.ghealth.open.api.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.open.api.restful.model.AccountDTO;
import com.todaysoft.ghealth.open.api.restful.request.AccountMatchRequest;
import com.todaysoft.ghealth.open.api.service.IAccountService;
import com.todaysoft.ghealth.open.api.service.credentials.CredentialsHolder;

@RestController
@RequestMapping("/open/accounts")
public class AccountAction
{
    @Autowired
    private IAccountService accountService;
    
    @RequestMapping(value = "/matches", method = RequestMethod.POST)
    public DataResponse<AccountDTO> getMatchesAccount(@RequestBody AccountMatchRequest request, CredentialsHolder holder)
    {
        return accountService.getMatchesAccount(request.getUsername(), request.getPassword(), holder);
    }
}
