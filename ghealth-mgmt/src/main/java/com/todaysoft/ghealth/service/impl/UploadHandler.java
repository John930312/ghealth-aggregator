package com.todaysoft.ghealth.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadHandler
{
    public void upload(String directory, String filename, String suffix, MultipartFile file)
        throws IOException
    {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)attributes).getRequest();
        String rootPath = request.getServletContext().getRealPath("/");
        
        File dir = new File(rootPath, directory);
        dir.mkdirs();
        FileCopyUtils.copy(file.getBytes(), new File(dir, filename + suffix));
    }
}
