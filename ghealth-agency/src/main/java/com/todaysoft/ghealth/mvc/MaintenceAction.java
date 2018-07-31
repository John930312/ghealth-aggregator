package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.mvc.view.DownloadUploadDateView;
import com.todaysoft.ghealth.support.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.View;

@Controller
@RequestMapping("/maintence")
public class MaintenceAction extends BaseAction
{
    @Autowired
    private DownloadUploadDateView view;
    
    @RequestMapping("/upload.do")
    public String upload()
    {
        return "maintence/maintence_upload";
    }
    
    @RequestMapping("/manipulateData.do")
    public View manipulateData(ModelMap model, MultipartFile txtData, MultipartFile excelData)
    {
        model.addAttribute("txtData", txtData);
        model.addAttribute("excelData", excelData);
        return view;
    }
}
