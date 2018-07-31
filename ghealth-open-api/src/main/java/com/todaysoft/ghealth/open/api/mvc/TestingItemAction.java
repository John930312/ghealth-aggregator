package com.todaysoft.ghealth.open.api.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hsgene.restful.response.DataResponse;
import com.hsgene.restful.util.CountRecords;
import com.todaysoft.ghealth.open.api.restful.model.TestingItemDTO;
import com.todaysoft.ghealth.open.api.restful.request.TestingItemQueryRequest;
import com.todaysoft.ghealth.open.api.service.ITestingItemService;
import com.todaysoft.ghealth.open.api.service.credentials.CredentialsHolder;

@RestController
@RequestMapping("/open/testing-items")
public class TestingItemAction
{
    @Autowired
    private ITestingItemService testingItemService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public DataResponse<CountRecords<TestingItemDTO>> list(@RequestBody(required = false) TestingItemQueryRequest request,
            CredentialsHolder holder)
    {
        if (null == request)
        {
            request = new TestingItemQueryRequest();
            request.setLimit(10);
        }

        return testingItemService.list(request);
    }
}
