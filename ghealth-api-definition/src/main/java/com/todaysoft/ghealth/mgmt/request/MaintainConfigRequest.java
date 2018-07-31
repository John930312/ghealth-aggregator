package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;
import org.springframework.web.multipart.MultipartFile;

public class MaintainConfigRequest extends SignatureTokenRequest
{
    private String id;
    
    private String configKey;
    
    private String configName;
    
    private String configValue;
    
    private Boolean configValueType;

    private String bytes;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("configKey", configKey);
        fields.put("configName", configName);
        fields.put("configValue", configValue);
        // fields.put("configValueType", configValueType);
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getConfigKey()
    {
        return configKey;
    }
    
    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }
    
    public String getConfigName()
    {
        return configName;
    }
    
    public void setConfigName(String configName)
    {
        this.configName = configName;
    }
    
    public String getConfigValue()
    {
        return configValue;
    }
    
    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }
    
    public Boolean getConfigValueType()
    {
        return configValueType;
    }
    
    public void setConfigValueType(Boolean configValueType)
    {
        this.configValueType = configValueType;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }
}
