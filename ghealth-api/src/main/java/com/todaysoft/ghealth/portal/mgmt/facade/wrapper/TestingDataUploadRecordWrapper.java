package com.todaysoft.ghealth.portal.mgmt.facade.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.TestingDataUploadRecordDTO;
import com.todaysoft.ghealth.mybatis.model.TestingDataUploadRecord;
import com.todaysoft.ghealth.service.wrapper.Wrapper;

@Component
public class TestingDataUploadRecordWrapper implements Wrapper<TestingDataUploadRecord, TestingDataUploadRecordDTO>
{
    @Override
    public List<TestingDataUploadRecordDTO> wrap(List<TestingDataUploadRecord> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        TestingDataUploadRecordDTO target;
        List<TestingDataUploadRecordDTO> targets = new ArrayList<TestingDataUploadRecordDTO>();
        
        for (TestingDataUploadRecord source : sources)
        {
            target = new TestingDataUploadRecordDTO();
            wrap(source, target);
            targets.add(target);
        }
        
        return targets;
    }
    
    public TestingDataUploadRecordDTO wrap(TestingDataUploadRecord source)
    {
        if (null == source)
        {
            return null;
        }
        
        TestingDataUploadRecordDTO target = new TestingDataUploadRecordDTO();
        wrap(source, target);
        return target;
    }
    
    private void wrap(TestingDataUploadRecord source, TestingDataUploadRecordDTO target)
    {
        BeanUtils.copyProperties(source, target, "uploadTime");
        target.setFileUri(source.getDownloadUrl());
        target.setUploadTime(null == source.getUploadTime() ? null : source.getUploadTime().getTime());
    }
}
