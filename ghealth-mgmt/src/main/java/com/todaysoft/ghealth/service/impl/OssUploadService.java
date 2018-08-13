package com.todaysoft.ghealth.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.todaysoft.ghealth.config.AliyunOSSConfig;
import com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.service.IOssUploadService;
import com.todaysoft.ghealth.utils.CallbackUtil;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: xjw
 * @Date: 2018/8/1 10:00
 */

@Service
public class OssUploadService implements IOssUploadService
{
    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;
    
    @Autowired
    private Gateway gateway;
    
    @Override
    public Map<String, String> getOssSign(Order order, String fileName)
    {
        String dir = getObjectKey(order.getCode(), aliyunOSSConfig.getDirectoryName());
        String formateName = order.getCode() + fileName.substring(fileName.indexOf("."));
        
        Map<String, String> respMap = new LinkedHashMap<>();
        OSSClient client = new OSSClient(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());
        try
        {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            
            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            
            Map callBackMap = new HashMap();
            callBackMap.put("callbackUrl", aliyunOSSConfig.getCallbackUrl());
            callBackMap.put("callbackBodyType", "application/json");
            callBackMap.put("callbackBody", "bucket=${bucket}&size=${size}&orderid=${x:orderid}&objectkey=${x:objectkey}");
            
            respMap.put("callback", new String(Base64.getEncoder().encode(JsonUtils.toJson(callBackMap).toString().getBytes())));
            respMap.put("dir", dir);
            respMap.put("objectKey", dir + formateName);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("accessid", aliyunOSSConfig.getAccessKeyId());
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            respMap.put("host", MessageFormat.format("http://{0}.{1}", aliyunOSSConfig.getBucketName(), aliyunOSSConfig.getEndpoint()));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != client)
            {
                client.shutdown();
            }
        }
        return respMap;
    }
    
    @Override
    public void callBack(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String ossCallbackBody = CallbackUtil.GetPostBody(request.getInputStream(), Integer.parseInt(request.getHeader("content-length")));
            if (StringUtils.isEmpty(ossCallbackBody))
            {
                throw new ServiceException("oss has not responseBody!");
            }
            boolean flag = CallbackUtil.VerifyOSSCallbackRequest(request, ossCallbackBody);
            //验证是否是oss请求
            if (!flag)
            {
                throw new ServiceException("not oss request!");
            }
            
            Map<String, String> callBackMap = new HashMap<String, String>();
            Arrays.stream(ossCallbackBody.split("&")).forEach(x -> {
                String[] datas = x.split("=");
                callBackMap.put(datas[0], datas[1].substring(1, datas[1].length() - 1));
            });
            
            MaintainOrderRequest orderRequest = new MaintainOrderRequest();
            orderRequest.setLogin(false);
            orderRequest.setId(callBackMap.get("orderid"));
            orderRequest.setObjectKey(callBackMap.get("objectkey"));
            gateway.request("/mgmt/order/uploadReport", orderRequest);
            if (flag)
            {
                CallbackUtil.response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
            }
            else
            {
                CallbackUtil.response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private String getObjectKey(String code, String directory)
    {
        String path = MessageFormat.format("report/{0}/{1}/", DateFormatUtils.format(new Date(), "yyyyMM"), code);
        String objectKey;
        
        if (StringUtils.isEmpty(directory))
        {
            objectKey = path;
        }
        else
        {
            if (directory.startsWith("/"))
            {
                directory = directory.substring(1);
            }
            
            if (StringUtils.isEmpty(directory))
            {
                objectKey = path;
            }
            else
            {
                if (!directory.endsWith("/"))
                {
                    directory += "/";
                }
                objectKey = directory + path;
            }
        }
        return objectKey;
    }
}
