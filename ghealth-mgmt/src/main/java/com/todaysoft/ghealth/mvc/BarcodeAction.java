package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.model.barcode.Barcode;
import com.todaysoft.ghealth.model.barcode.BarcodeSearcher;
import com.todaysoft.ghealth.service.IBarcodeServcie;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/barcode")
public class BarcodeAction extends BaseAction
{
    @Autowired
    private IBarcodeServcie barcodeServcie;
    
    @RequestMapping("/list.jsp")
    public String list(BarcodeSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Barcode> pagination = barcodeServcie.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "barcode/barcode_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    public String create()
    {
        return "barcode/barcode_create";
    }
    
    @ResponseBody
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    public Integer create(Barcode data, ModelMap model, HttpSession session)
    {
        return barcodeServcie.add(data);
    }
}
