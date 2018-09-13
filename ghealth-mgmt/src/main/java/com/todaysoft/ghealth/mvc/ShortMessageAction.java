package com.todaysoft.ghealth.mvc;

import com.todaysoft.document.generate.sdk.utils.JsonUtils;
import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import com.todaysoft.ghealth.model.shortMessage.ShortMessage;
import com.todaysoft.ghealth.model.shortMessage.ShortMessageCon;
import com.todaysoft.ghealth.model.shortMessage.ShortMessageForm;
import com.todaysoft.ghealth.model.shortMessage.SmsSend;
import com.todaysoft.ghealth.service.IMessageSendService;
import com.todaysoft.ghealth.service.IShortMessageService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shortMessage")
public class ShortMessageAction extends BaseAction
{
    
    @Autowired
    private IMessageSendService messageSendService;
    
    @Autowired
    private IShortMessageService service;
    
    @RequestMapping("/list.jsp")
    public String list(ModelMap model, HttpSession session)
    {
        List<ShortMessage> shortMessages = new ArrayList<>();
        shortMessages = service.getShortMessage(false);
        model.addAttribute("data", shortMessages);
        
        shortMessages = service.getShortMessage(true);
        model.addAttribute("message", shortMessages);

        return "shortMessage/shortMessage_list";
    }
    
    @RequestMapping("/modify.jsp")
    public String modify(ShortMessageForm data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/shortMessage/list.jsp");
    }
    
    @RequestMapping("/create.jsp")
    public String create(ShortMessageForm data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, "/shortMessage/list.jsp");
    }
    
    @RequestMapping(value = "/delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "/shortMessage/list.jsp");
    }
    
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public ShortMessage get(String id)
    {
        ShortMessage shortMessage = service.get(id);
        return shortMessage;
    }
    
    @RequestMapping(value = "/getByAgencyId", method = RequestMethod.POST)
    @ResponseBody
    public boolean getByAgencyId(String agencyId)
    {
        
        return service.getByAgencyId(agencyId);
    }
    
    @RequestMapping(value = "/festival_create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String festival_create(ModelMap model)
    {
        model.addAttribute("agencies", messageSendService.getAgencyList());
        return "shortMessage/shortMessage_festival_create";
    }
    
    @RequestMapping(value = "/festival_create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String festival_create(SmsSend data, HttpServletRequest request, ModelMap model, HttpSession session)
    {
        data.setStatus("4");
        service.create(data);
        return redirectList(model, session, "/shortMessage/list.jsp");
    }
    
}
