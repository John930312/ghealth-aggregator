package com.todaysoft.ghealth.model;

public class OrderUploadModel
{
    private boolean isMach;
    
    private Object text;
    
    public boolean getIsMach()
    {
        return isMach;
    }
    
    public void setMach(boolean mach)
    {
        isMach = mach;
    }
    
    public Object getText()
    {
        return text;
    }
    
    public void setText(Object text)
    {
        this.text = text;
    }
}
