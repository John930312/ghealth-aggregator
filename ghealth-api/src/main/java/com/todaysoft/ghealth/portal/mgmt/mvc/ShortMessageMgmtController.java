package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.ShortMessage;
import com.todaysoft.ghealth.mgmt.request.MaintainShortMessageRequest;
import com.todaysoft.ghealth.mgmt.request.QueryShortMessageRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.ShortMessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/shortMessage")
public class ShortMessageMgmtController {
    @Autowired
    private ShortMessageFacade facade;

    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainShortMessageRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/getShortMessage")
    public ListResponse<ShortMessage> getShortMessage(@RequestBody QueryShortMessageRequest request)
    {
        return facade.getShortMessage(request);
    }

    @RequestMapping("/create")
    public void create(@RequestBody MaintainShortMessageRequest request)
    {
        facade.create(request);
    }


    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainShortMessageRequest request)
    {
        facade.delete(request);
    }


    @RequestMapping("/details")
    public ObjectResponse<ShortMessage> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }


    @RequestMapping("/getByAgencyId")
    public ObjectResponse<Boolean> getByAgencyId(@RequestBody QueryShortMessageRequest request)
    {
        return facade.getByAgencyId(request);
    }

    @RequestMapping("/getMessage")
    public ObjectResponse<ShortMessage> getMessage(@RequestBody QueryShortMessageRequest request)
    {
        return facade.getMessage(request);
    }
}
