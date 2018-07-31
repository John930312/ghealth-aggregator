package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.TestingDataUploadRecordDTO;
import com.todaysoft.ghealth.model.dataupload.TestingDataUploadRecord;

@Component
public class TestingDataUploadRecordWrapper
{
    public List<TestingDataUploadRecord> wrap(List<TestingDataUploadRecordDTO> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        TestingDataUploadRecord target;
        List<TestingDataUploadRecord> targets = new ArrayList<TestingDataUploadRecord>();
        
        for (TestingDataUploadRecordDTO source : sources)
        {
            target = new TestingDataUploadRecord();
            wrap(source, target);
            targets.add(target);
        }
        
        return targets;
    }
    
    public TestingDataUploadRecord wrap(TestingDataUploadRecordDTO source)
    {
        if (null == source)
        {
            return null;
        }
        
        TestingDataUploadRecord target = new TestingDataUploadRecord();
        wrap(source, target);
        return target;
    }
    
    private void wrap(TestingDataUploadRecordDTO source, TestingDataUploadRecord target)
    {
        BeanUtils.copyProperties(source, target, "uploadTime");
        target.setUploadTime(null == source.getUploadTime() ? null : new Date(source.getUploadTime()));
    }
}
