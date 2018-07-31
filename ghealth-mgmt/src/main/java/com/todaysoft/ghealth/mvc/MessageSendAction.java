package com.todaysoft.ghealth.mvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/messageSend")
public class MessageSendAction extends BaseAction {
    @Autowired
    private IMessageSendService service;


    @RequestMapping("/list.jsp")
    public String list(MessageSendSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session) {
        Pagination<MessageSend> pagination = service.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "messageSend/messageSend_list";
    }


    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model) {
        model.addAttribute("agencies",service.getAgencyList());
        return "messageSend/messageSend_create";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(MultipartFile file, MessageSend messageSend, HttpServletRequest request, ModelMap model, HttpSession session) throws UnsupportedEncodingException {

        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotEmpty(fileName))
        {
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            fileName = name + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + fileF;
        }
        try {
            byte[] bytes = file.getBytes();
            messageSend.setFileByte(bytes);



        } catch (IOException e) {
            e.printStackTrace();
        }

        messageSend.setPath(fileName);

        service.create(messageSend);
        return redirectList(model, session, "/messageSend/list.jsp");
    }



    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model, HttpServletRequest request,HttpSession session)
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

    @RequestMapping("/isNameUnique.do")
    @ResponseBody
    public boolean isTitleUnique(String title)
    {
        return service.isTitleUnique(title);
    }


    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap map, HttpSession session)
    {

        MessageSend messageSend = service.get(id);

        if (StringUtils.isNotEmpty(messageSend.getAddress())){
            String add = messageSend.getAddress();
            Map<String, String> attributes = JsonUtils.fromJson(add, new TypeReference<Map<String, String>>()
            {
            });
            String object = attributes.get("objectKey");
            object = object.substring(object.lastIndexOf("/")+1);


            map.addAttribute("address",object);
        }

        map.addAttribute("data", messageSend);
        return "messageSend/messageSend_modify";
    }

    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(MessageSend messageSend, ModelMap model, HttpSession session)
    {
        service.modify(messageSend);
        return redirectList(model, session, "/messageSend/list.jsp");
    }

    @RequestMapping(value = "/dataDownload.do", method = RequestMethod.POST)
    public String dataDownload( HttpServletRequest request, HttpServletResponse response,String url,String pageName,ModelMap model,HttpSession session){

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

    @RequestMapping(value = "delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "/messageSend/list.jsp");
    }


}
