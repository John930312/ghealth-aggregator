package com.todaysoft.ghealth.service.impl.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.todaysoft.ghealth.config.AliyunOSSConfig;

@Component
public class AliyunOSSHandler
{
    @Autowired
    private AliyunOSSConfig config;
    
    public void upload(String objectKey, File file)
        throws IOException
    {
        this.verifyUploadConfig();
        String endpoint = "http://" + config.getEndpoint();
        String accessKeyId = config.getAccessKeyId();
        String accessKeySecret = config.getAccessKeySecret();
        String bucketName = config.getBucketName();
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = new FileInputStream(file);
        client.putObject(bucketName, objectKey, inputStream);
        client.shutdown();
    }
    
    public void delete(String endpoint, String bucketName, String objectKey)
    {
        this.verifyDeleteConfig();
        String accessKeyId = config.getAccessKeyId();
        String accessKeySecret = config.getAccessKeySecret();
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        client.deleteObject(bucketName, objectKey);
        client.shutdown();
    }
    
    public byte[] download(String endpoint, String bucketName, String objectKey)
        throws IOException
    {
        this.verifyDownloadConfig();
        String accessKeyId = config.getAccessKeyId();
        String accessKeySecret = config.getAccessKeySecret();
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject object = client.getObject(bucketName, objectKey);
        
        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try
        {
            in = object.getObjectContent();
            IOUtils.copy(in, out);
            client.shutdown();
            return out.toByteArray();
        }
        finally
        {
            if (null != in)
            {
                in.close();
            }
        }
    }
    
    private void verifyUploadConfig()
    {
        if (StringUtils.isEmpty(config.getAccessKeyId()) || StringUtils.isEmpty(config.getAccessKeySecret()) || StringUtils.isEmpty(config.getEndpoint())
            || StringUtils.isEmpty(config.getBucketName()))
        {
            throw new IllegalStateException("Aliyun oss config illegal.");
        }
    }
    
    private void verifyDownloadConfig()
    {
        if (StringUtils.isEmpty(config.getAccessKeyId()) || StringUtils.isEmpty(config.getAccessKeySecret()))
        {
            throw new IllegalStateException("Aliyun oss config illegal.");
        }
    }
    
    private void verifyDeleteConfig()
    {
        if (StringUtils.isEmpty(config.getAccessKeyId()) || StringUtils.isEmpty(config.getAccessKeySecret()))
        {
            throw new IllegalStateException("Aliyun oss config illegal.");
        }
    }
}
