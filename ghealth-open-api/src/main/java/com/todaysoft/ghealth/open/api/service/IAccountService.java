package com.todaysoft.ghealth.open.api.service;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.open.api.restful.model.AccountDTO;
import com.todaysoft.ghealth.open.api.service.credentials.CredentialsHolder;

public interface IAccountService
{
    DataResponse<AccountDTO> getMatchesAccount(String username, String password, CredentialsHolder holder);
}
