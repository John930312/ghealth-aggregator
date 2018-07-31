package com.todaysoft.ghealth.base.request;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class SignatureRequest
{
    private String accessKey;
    
    private Long timestamp;
    
    private String signature;
    
    public void sign(String accessKey, String secretKey)
    {
        this.accessKey = accessKey;
        this.timestamp = new Date().getTime();
        this.signature = sign(secretKey);
    }
    
    public boolean isValid(String secretKey)
    {
        return null != signature && signature.equals(sign(secretKey));
    }
    
    protected String sign(String secretKey)
    {
        if (null == accessKey || null == secretKey || null == timestamp)
        {
            throw new IllegalArgumentException();
        }
        
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("accessKey", accessKey);
        fields.put("timestamp", String.valueOf(timestamp));
        setSignFields(fields);
        
        TreeMap<String, String> sort = new TreeMap<String, String>(fields);
        
        StringBuffer buf = new StringBuffer();
        
        for (Map.Entry<String, String> entry : sort.entrySet())
        {
            if (null == entry.getValue() || 0 == entry.getValue().length())
            {
                continue;
            }
            
            buf.append(entry.getKey());
            buf.append(entry.getValue());
        }
        
        buf.append(secretKey);
        return DigestUtils.md5Hex(buf.toString()).toUpperCase();
    }
    
    protected abstract void setSignFields(Map<String, String> fields);
    
    public String getAccessKey()
    {
        return accessKey;
    }
    
    public void setAccessKey(String accessKey)
    {
        this.accessKey = accessKey;
    }
    
    public Long getTimestamp()
    {
        return timestamp;
    }
    
    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }
    
    public String getSignature()
    {
        return signature;
    }
    
    public void setSignature(String signature)
    {
        this.signature = signature;
    }
}
