package com.todaysoft.ghealth.model.barcode;

public class BarcodeSearcher
{
    private String prefixCode;
    
    private String barCodeComplete;
    
    private String isFree;
    
    public String getPrefixCode()
    {
        return prefixCode;
    }
    
    public void setPrefixCode(String prefixCode)
    {
        this.prefixCode = prefixCode;
    }
    
    public String getBarCodeComplete()
    {
        return barCodeComplete;
    }
    
    public void setBarCodeComplete(String barCodeComplete)
    {
        this.barCodeComplete = barCodeComplete;
    }
    
    public String getIsFree()
    {
        return isFree;
    }
    
    public void setIsFree(String isFree)
    {
        this.isFree = isFree;
    }
}
