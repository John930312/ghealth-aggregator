package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.todaysoft.ghealth.model.barcode.Barcode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BarcodeWrapper
{
    public List<Barcode> wrap(List<com.todaysoft.ghealth.base.response.model.Barcode> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Barcode data;
        List<Barcode> Barcodes = new ArrayList<Barcode>();
        for (com.todaysoft.ghealth.base.response.model.Barcode record : records)
        {
            data = new Barcode();
            wrapRecord(record, data);
            Barcodes.add(data);
        }
        return Barcodes;
    }
    
    public Barcode wrap(com.todaysoft.ghealth.base.response.model.Barcode record)
    {
        if (null == record)
        {
            return null;
        }
        
        Barcode data = new Barcode();
        wrapRecord(record, data);
        return data;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Barcode source, Barcode target)
    {
        BeanUtils.copyProperties(source, target);
    }
}
