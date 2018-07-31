package com.todaysoft.ghealth.mgmt.request;

import java.util.List;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;
import com.todaysoft.ghealth.base.response.model.OrderUploadRequest;

public class MaintainOrderUploadRequest extends SignatureTokenRequest
{
    private List<OrderUploadRequest> list;
    
    public List<OrderUploadRequest> getList()
    {
        return list;
    }
    
    public void setList(List<OrderUploadRequest> list)
    {
        this.list = list;
    }
}
