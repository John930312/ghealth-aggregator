package com.todaysoft.ghealth.model.dataupload;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.locus.Locus;

public class TestingDataUploadDetails extends TestingDataUploadForm
{
    private int validGenetypeCount;
    
    private int invalidGenetypeCount;
    
    private List<TestingDataUploadHeader<Locus>> rows;
    
    private List<TestingDataUploadHeader<Order>> columns;
    
    private Map<String, TestingDataUploadGenetype> genetypes;
    
    public TestingDataUploadGenetype getGenetype(int row, int column)
    {
        if (CollectionUtils.isEmpty(genetypes))
        {
            return null;
        }
        
        String key = (row) + "-" + (column);
        TestingDataUploadGenetype genetype = genetypes.get(key);
        
        if (null != genetype && StringUtils.isEmpty(genetype.getGenetype()))
        {
            TestingDataUploadGenetype wrapped = new TestingDataUploadGenetype();
            wrapped.setGenetype("  ");
            wrapped.setMessage(genetype.getMessage());
            wrapped.setValid(genetype.isValid());
            return wrapped;
        }
        
        return genetype;
    }
    
    public int getValidGenetypeCount()
    {
        return validGenetypeCount;
    }
    
    public void setValidGenetypeCount(int validGenetypeCount)
    {
        this.validGenetypeCount = validGenetypeCount;
    }
    
    public int getInvalidGenetypeCount()
    {
        return invalidGenetypeCount;
    }
    
    public void setInvalidGenetypeCount(int invalidGenetypeCount)
    {
        this.invalidGenetypeCount = invalidGenetypeCount;
    }
    
    public List<TestingDataUploadHeader<Locus>> getRows()
    {
        return rows;
    }
    
    public void setRows(List<TestingDataUploadHeader<Locus>> rows)
    {
        this.rows = rows;
    }
    
    public List<TestingDataUploadHeader<Order>> getColumns()
    {
        return columns;
    }
    
    public void setColumns(List<TestingDataUploadHeader<Order>> columns)
    {
        this.columns = columns;
    }
    
    public Map<String, TestingDataUploadGenetype> getGenetypes()
    {
        return genetypes;
    }
    
    public void setGenetypes(Map<String, TestingDataUploadGenetype> genetypes)
    {
        this.genetypes = genetypes;
    }
}
