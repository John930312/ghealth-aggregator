package com.todaysoft.ghealth.mvc.view;

import com.todaysoft.ghealth.agcy.request.MaintainOrderRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.service.impl.Gateway;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component("downloadOrderDataView")
public class DownloadOrderDataView extends AbstractView
{
    @Autowired
    private Gateway gateway;
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String orderIds = (String)model.get("orderIds");
        if (StringUtils.isEmpty(orderIds))
        {
            throw new IllegalStateException();
        }
        MaintainOrderRequest orderRequest = new MaintainOrderRequest();
        orderRequest.setId(orderIds);
        orderRequest.setLogin(false);
        ObjectResponse<byte[]> result =
            gateway.request("/agcy/order/getOrderReportDatas", orderRequest, new ParameterizedTypeReference<ObjectResponse<byte[]>>()
            {
            });
        InputStream input = new ByteArrayInputStream(result.getData());
        String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + "_" + new SimpleDateFormat("HHmm").format(new Date()) + ".txt";
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        
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
