package com.todaysoft.ghealth.mvc.view;

import com.todaysoft.ghealth.service.IMaintenceService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class DownloadUploadDateView extends AbstractView
{
    @Autowired
    private IMaintenceService service;
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
    {
        MultipartFile txtData = (MultipartFile)model.get("txtData");
        MultipartFile excelData = (MultipartFile)model.get("excelData");
        
        if (Objects.isNull(txtData) || Objects.isNull(excelData))
        {
            throw new IllegalStateException();
        }
        
        String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + "_" + new SimpleDateFormat("HHmm").format(new Date()) + ".xlsx";
        File file = null;
        OutputStream out = null;
        InputStream in = null;
        try
        {
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            
            file = service.manipulateData(txtData, excelData);
            in = new FileInputStream(file);
            out = response.getOutputStream();
            
            IOUtils.copy(in, out);
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != in)
                {
                    in.close();
                }
                if (null != out)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            if (null != file)
            {
                file.delete();
            }
        }
    }
}
