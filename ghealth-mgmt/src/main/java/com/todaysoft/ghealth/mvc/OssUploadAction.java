package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.service.IOssUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: xjw
 * @Date: 2018/8/1 9:47
 */
@Controller
@RequestMapping("/oss")
public class OssUploadAction
{
    @Autowired
    private IOssUploadService ossUploadService;
    
    @RequestMapping("/getSign")
    @ResponseBody
    public Map<String, String> getOssSign(Order order, String fileName)
    {
        return ossUploadService.getOssSign(order, fileName);
    }
    
    @RequestMapping("/callBack")
    public void callBack(HttpServletRequest request, HttpServletResponse response)
    {
        ossUploadService.callBack(request, response);
    }
    
}
