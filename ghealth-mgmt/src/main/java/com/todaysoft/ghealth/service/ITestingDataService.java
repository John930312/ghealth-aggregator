package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.dataupload.TestingDataUploadDetails;
import com.todaysoft.ghealth.model.dataupload.TestingDataUploadForm;
import com.todaysoft.ghealth.model.dataupload.TestingDataUploadRecord;
import com.todaysoft.ghealth.model.dataupload.TestingDataUploadRecordSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ITestingDataService
{
    Pagination<TestingDataUploadRecord> search(TestingDataUploadRecordSearcher searcher, int pageNo, int pageSize);
    
    TestingDataUploadRecord get(String id);
    
    TestingDataUploadDetails parse(TestingDataUploadForm data);
    
    void upload(TestingDataUploadDetails details, boolean ignoreInvalidGenetypes, boolean overUpload);
}
