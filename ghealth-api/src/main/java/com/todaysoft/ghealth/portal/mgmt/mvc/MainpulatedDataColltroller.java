package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.portal.mgmt.facade.MainpulatedDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/template")
public class MainpulatedDataColltroller
{
    @Autowired
    private MainpulatedDataFacade facade;
    
    @RequestMapping("/mainpulatedData")
    public void mainpulatedData()
    {
        facade.mainpulatedData();
    }
}
