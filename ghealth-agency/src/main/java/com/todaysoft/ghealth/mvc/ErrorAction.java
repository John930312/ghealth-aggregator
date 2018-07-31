package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.support.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorAction extends BaseAction
{
    @RequestMapping("/error")
    public String error()
    {
        return "error";
    }
}
