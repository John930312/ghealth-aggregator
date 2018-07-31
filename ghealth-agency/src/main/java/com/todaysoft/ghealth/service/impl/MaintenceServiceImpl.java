package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.model.UploadData;
import com.todaysoft.ghealth.service.IMaintenceService;
import com.todaysoft.ghealth.utils.UploadHandle;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

@Service
public class MaintenceServiceImpl implements IMaintenceService
{
    @Override
    public File manipulateData(MultipartFile txtData, MultipartFile excelData) throws IOException
    {
        UploadHandle uploadHandle = new UploadHandle();
        
        Map<String, UploadData> dataMap = uploadHandle.readTxtByScanner(txtData.getInputStream());
        if (!CollectionUtils.isEmpty(dataMap))
        {
            return uploadHandle.readExcel(excelData, dataMap);
        }
        return null;
    }
    
}
