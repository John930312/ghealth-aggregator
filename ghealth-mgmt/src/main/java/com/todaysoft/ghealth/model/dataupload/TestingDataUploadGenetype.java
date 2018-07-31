package com.todaysoft.ghealth.model.dataupload;

public class TestingDataUploadGenetype
{
    private boolean valid;
    
    private boolean overUpload;
    
    private String message;
    
    private String genetype;
    
    public boolean isValid()
    {
        return valid;
    }
    
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getGenetype()
    {
        return genetype;
    }
    
    public void setGenetype(String genetype)
    {
        this.genetype = genetype;
    }
    
    public boolean isOverUpload()
    {
        return overUpload;
    }
    
    public void setOverUpload(boolean overUpload)
    {
        this.overUpload = overUpload;
    }
}
