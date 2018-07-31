package com.todaysoft.ghealth.mgmt.request;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;
import com.todaysoft.ghealth.mgmt.model.OrderTestingDataDTO;

public class TestingDataUploadRequest extends SignatureTokenRequest
{
    private String title;
    
    private String filename;
    
    private String fileUri;
    
    private List<OrderTestingDataDTO> orderGenetypes;

    private byte[] fileByte;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        fields.put("title", title);
        fields.put("filename", filename);
        fields.put("fileUri", fileUri);
        fields.put("orderGenetypes", CollectionUtils.isEmpty(orderGenetypes) ? "0" : String.valueOf(orderGenetypes.size()));
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getFilename()
    {
        return filename;
    }
    
    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    
    public String getFileUri()
    {
        return fileUri;
    }
    
    public void setFileUri(String fileUri)
    {
        this.fileUri = fileUri;
    }
    
    public List<OrderTestingDataDTO> getOrderGenetypes()
    {
        return orderGenetypes;
    }
    
    public void setOrderGenetypes(List<OrderTestingDataDTO> orderGenetypes)
    {
        this.orderGenetypes = orderGenetypes;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }
}
