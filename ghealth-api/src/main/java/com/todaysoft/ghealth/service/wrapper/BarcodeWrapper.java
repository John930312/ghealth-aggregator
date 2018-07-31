package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Barcode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BarcodeWrapper
{
    public List<com.todaysoft.ghealth.base.response.model.Barcode> wrap(List<Barcode> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        com.todaysoft.ghealth.base.response.model.Barcode data;
        List<com.todaysoft.ghealth.base.response.model.Barcode> datas = new ArrayList<com.todaysoft.ghealth.base.response.model.Barcode>();
        for (Barcode record : records)
        {
            data = new com.todaysoft.ghealth.base.response.model.Barcode();
            wrapRecord(record, data);
            datas.add(data);
        }
        return datas;
    }
    
    public com.todaysoft.ghealth.base.response.model.Barcode wrap(Barcode record)
    {
        if (null == record)
        {
            return null;
        }
        
        com.todaysoft.ghealth.base.response.model.Barcode data = new com.todaysoft.ghealth.base.response.model.Barcode();
        wrapRecord(record, data);
        return data;
    }
    
    private void wrapRecord(Barcode source, com.todaysoft.ghealth.base.response.model.Barcode target)
    {
        BeanUtils.copyProperties(source, target);
    }
}
