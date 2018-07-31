package com.todaysoft.ghealth.mvc.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class DownloadFileView extends AbstractView
{
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        String name = (String)model.get("name");
        InputStream input = (InputStream)model.get("inputStream");
        
        if (null == input || StringUtils.isEmpty(name))
        {
            throw new IllegalStateException();
        }
        
        response.setHeader("Content-disposition", "attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1"));
        
        OutputStream out = response.getOutputStream();
        
        try
        {
            IOUtils.copy(input, out);
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        }
        finally
        {
            if (null != input)
            {
                input.close();
            }
            
            if (null != out)
            {
                out.close();
            }
        }
    }
}
