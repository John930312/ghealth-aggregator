package com.todaysoft.ghealth.mvc.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.todaysoft.document.generate.sdk.DocumentGenerateSDK;
import com.todaysoft.document.generate.sdk.response.TemplateFile;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplate;
import com.todaysoft.ghealth.service.IReportTemplateService;
import com.todaysoft.ghealth.service.impl.DocumentGenerateConfig;

@Component("reportTemplateDownloadView")
public class ReportTemplateDownloadView extends AbstractView
{
    @Autowired
    private IReportTemplateService reportTemplateService;
    
    @Autowired
    private DocumentGenerateConfig generateConfig;
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        String id = (String)model.get("id");
        
        if (StringUtils.isEmpty(id))
        {
            return;
        }
        
        ReportTemplate template = reportTemplateService.get(id);
        
        if (null == template)
        {
            return;
        }
        
        DocumentGenerateSDK sdk = new DocumentGenerateSDK(generateConfig.getEndpoint(), generateConfig.getAccessKeyId(), generateConfig.getAccessKeySecret());
        TemplateFile templateFile = sdk.downloadTemplate(template.getTsdgKey());
        
        if (null == templateFile)
        {
            return;
        }
        
        String filename = template.getName() + templateFile.getSuffix();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", String.format("attachment; filename=%1$s", URLEncoder.encode(filename, "UTF-8")));
        response.setContentType("application/octet-stream");
        OutputStream out = response.getOutputStream();
        
        InputStream in = null;
        
        try
        {
            in = templateFile.getContent();
            IOUtils.copy(in, out);
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        }
        finally
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
    }
}
