package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Email;
import com.todaysoft.ghealth.mgmt.request.MaintainEmailRequest;

import com.todaysoft.ghealth.mgmt.request.QueryEmailRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.EmailMgmtFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/email")
public class EmailMgmtController {
    @Autowired
    private EmailMgmtFacade facade;

    @RequestMapping("/pager")
    public PagerResponse<Email> pager(@RequestBody QueryEmailRequest request)
    {
        return facade.pager(request);
    }


    @RequestMapping("/details")
    public ObjectResponse<Email> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }

    @RequestMapping("/create")
    public void create(@RequestBody MaintainEmailRequest request)
    {
        facade.create(request);
    }


    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainEmailRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainEmailRequest request)
    {
        facade.delete(request);
    }


    @RequestMapping("/getList")
    public ListResponse<Email> getList(@RequestBody MaintainEmailRequest request)
    {
        return facade.getList(request);
    }
}
