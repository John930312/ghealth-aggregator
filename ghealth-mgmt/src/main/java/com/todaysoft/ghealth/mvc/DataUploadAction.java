package com.todaysoft.ghealth.mvc;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import com.todaysoft.ghealth.model.dataupload.TestingDataUploadDetails;
import com.todaysoft.ghealth.model.dataupload.TestingDataUploadForm;
import com.todaysoft.ghealth.model.dataupload.TestingDataUploadRecord;
import com.todaysoft.ghealth.model.dataupload.TestingDataUploadRecordSearcher;
import com.todaysoft.ghealth.mvc.view.DownloadFileView;
import com.todaysoft.ghealth.service.ITestingDataService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.ModelResolver;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/dataupload")
public class DataUploadAction extends BaseAction
{
    @Autowired
    private DownloadFileView downloadFileView;
    
    @Autowired
    private ITestingDataService service;
    
    @RequestMapping("/list.jsp")
    public String list(TestingDataUploadRecordSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<TestingDataUploadRecord> pagination = service.search(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "dataupload/dataupload_list";
    }
    
    @RequestMapping(value = "/upload.jsp", method = RequestMethod.GET)
    
    public String upload(ModelMap model)
    {
        return "dataupload/upload_form";
    }
    
    @RequestMapping(value = "/parse.jsp")
    @FormInputView
    public String parse(TestingDataUploadForm data, ModelMap model, HttpSession session)
    {
        TestingDataUploadDetails details = service.parse(data);
        String token = UUID.randomUUID().toString();
        session.setAttribute("TESTING_DATA_UPLOAD_TOKEN", token);
        session.setAttribute("TESTING_DATA_UPLOAD_DETAILS", details);
        model.addAttribute("token", token);
        model.addAttribute("details", details);
        return "dataupload/upload_records";
    }
    
    @RequestMapping(value = "/upload.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String upload(String token, boolean ignoreInvalidGenetypes, boolean overUpload, ModelMap model, HttpSession session)
    {
        String stoken = (String)session.getAttribute("TESTING_DATA_UPLOAD_TOKEN");
        
        if (!StringUtils.isEmpty(stoken) && stoken.equals(token))
        {
            TestingDataUploadDetails details = (TestingDataUploadDetails)session.getAttribute("TESTING_DATA_UPLOAD_DETAILS");
            session.removeAttribute("TESTING_DATA_UPLOAD_TOKEN");
            session.removeAttribute("TESTING_DATA_UPLOAD_DETAILS");
            service.upload(details, ignoreInvalidGenetypes, overUpload);
        }
        
        return redirect(model, session);
    }
    
    private String redirect(ModelMap model, HttpSession session)
    {
        model.clear();
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/dataupload/list.jsp";
    }
    
    @RequestMapping("/download.jsp")
    public View download(String id, ModelMap model, HttpServletRequest request) throws IOException
    {
        TestingDataUploadRecord data = service.get(id);
        URL url = new URL(data.getFileUri());
        model.put("name", data.getFilename());
        model.put("inputStream", url.openConnection().getInputStream());
        return downloadFileView;
    }
}
