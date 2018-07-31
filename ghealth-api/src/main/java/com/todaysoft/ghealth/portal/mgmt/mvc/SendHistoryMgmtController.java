package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.SignInHistory;
import com.todaysoft.ghealth.mgmt.request.QuerySignInHistoryRequest;
import com.todaysoft.ghealth.mgmt.request.QuerySignInOrderDetailRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.SendHistoryMgmtFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/sendHistory")
public class SendHistoryMgmtController {
    @Autowired
    private SendHistoryMgmtFacade facade;

    @RequestMapping("pager")
    public PagerResponse<SignInHistory> pager(@RequestBody QuerySignInHistoryRequest request)
    {
        return facade.pager(request);
    }

    @RequestMapping("getSearch")
    public ListResponse<SignInHistory> getSearch(@RequestBody QuerySignInOrderDetailRequest request)
    {
        return facade.getSearch(request);
    }
}
