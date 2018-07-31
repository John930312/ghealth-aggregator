package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.service.orderEvent.OrderEventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.TestingDataUploadRecordDTO;
import com.todaysoft.ghealth.mgmt.request.QueryTestingDataUploadRecordsRequest;
import com.todaysoft.ghealth.mgmt.request.TestingDataUploadRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.TestingDataMgmtFacade;

import java.util.Map;

@RestController
@RequestMapping("/mgmt/testing-data")
public class TestingDataMgmtController
{
    @Autowired
    private TestingDataMgmtFacade facade;
    
    @RequestMapping("/upload-records/pager")
    public PagerResponse<TestingDataUploadRecordDTO> pager(@RequestBody QueryTestingDataUploadRecordsRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/upload-records/details")
    public ObjectResponse<TestingDataUploadRecordDTO> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/upload")
    @OrderEventLog(eventType = "5",handler = "mgmt")
    public Map<String, String> upload(@RequestBody TestingDataUploadRequest request)
    {
        return facade.upload(request);
    }
    
    //    @RequestMapping("/getData")
    //    public ObjectResponse<TestingDataUploadRecordDTO> findData(@RequestBody QueryDetailsRequest request)
    //    {
    //        return facade.findData(request);
    //    }
}
