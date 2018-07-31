package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.service.IMainpulatedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/template")
public class MainpulatedDataAction
{
    @Autowired
    private IMainpulatedDataService service;
    
    @RequestMapping("/mainpulatedData.do")
    @ResponseBody
    public String manipulatedData()
    {
        service.mainpulatedData();
        return "success";
    }
    
}
