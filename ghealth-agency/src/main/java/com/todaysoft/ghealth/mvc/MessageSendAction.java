package com.todaysoft.ghealth.mvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.model.messageSend.MessageSend;
import com.todaysoft.ghealth.model.messageSend.MessageSendSearcher;
import com.todaysoft.ghealth.service.IMessageSendService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

@Controller
@RequestMapping("/messageSend")
public class MessageSendAction extends BaseAction {

    @Autowired
    private IMessageSendService service;

    @RequestMapping("/list.jsp")
    public String paginations(MessageSendSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<MessageSend> pagination = service.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "messageSend/messageSend_list";
    }

    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model, HttpServletRequest request, HttpSession session)
    {
        MessageSend messageSend = service.get(id);
        if (StringUtils.isNotEmpty(messageSend.getAddress())){
            String add = messageSend.getAddress();

            Map<String, String> attributes = JsonUtils.fromJson(add, new TypeReference<Map<String, String>>()
            {
            });
            String object = attributes.get("objectKey");
            object = object.substring(object.lastIndexOf("/")+1);


            model.addAttribute("address",object);

        }
        model.addAttribute("data", messageSend);
        return "messageSend/messageSend_details";
    }

    @RequestMapping(value = "/dataDownload.do", method = RequestMethod.POST)
    public String dataDownload(HttpServletRequest request, HttpServletResponse response, String url,String pageName, ModelMap model, HttpSession session){

        try {
            URL url1 = new URL(url);
            InputStream is = url1.openConnection().getInputStream();
            response.setHeader("Content-disposition", "attachment;filename=" + new String(pageName.getBytes("gb2312"), "ISO8859-1"));

            OutputStream out = response.getOutputStream();
            try
            {
                IOUtils.copy(is, out);
                response.setStatus(HttpServletResponse.SC_OK);
                response.flushBuffer();
            }
            finally
            {
                if (null != is)
                {
                    is.close();
                }

                if (null != out)
                {
                    out.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirectList(model, session, "/messageSend/list.jsp");
    }
}
