package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Barcode;
import com.todaysoft.ghealth.mgmt.request.MaintainBarcodeRequest;
import com.todaysoft.ghealth.mgmt.request.QueryBarcodeRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.BarcodeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/barcode")
public class BarcodeController
{
    @Autowired
    private BarcodeFacade barcodeFacade;
    
    @RequestMapping("/pager")
    public PagerResponse<Barcode> pager(@RequestBody QueryBarcodeRequest request)
    {
        return barcodeFacade.pager(request);
    }
    
    @RequestMapping("/create")
    public Integer create(@RequestBody MaintainBarcodeRequest request)
    {
        return barcodeFacade.create(request);
    }
}
