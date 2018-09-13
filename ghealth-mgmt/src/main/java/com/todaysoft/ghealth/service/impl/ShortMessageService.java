package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.AgencyDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainShortMessageRequest;
import com.todaysoft.ghealth.mgmt.request.MaintainSmsSendRequest;
import com.todaysoft.ghealth.mgmt.request.QueryShortMessageRequest;
import com.todaysoft.ghealth.model.shortMessage.*;
import com.todaysoft.ghealth.service.IShortMessageService;
import com.todaysoft.ghealth.service.wrapper.ShortMessageWrapper;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShortMessageService implements IShortMessageService{

    @Autowired
    private Gateway gateway;

    @Autowired

    private ShortMessageWrapper wrapper;


    @Override
    public void modify(ShortMessageForm data)
    {
        SampleSigned sampleSigned = new SampleSigned();
        sampleSigned.setNotifyEnabled(data.getNotifyEnabled_sampleSigned());
        if (data.getNotifyEnabled_sampleSigned().equals("1")){
            sampleSigned.setNotifyTarget(data.getNotifyTarget_sampleSigned());
        }
        SampleDelivered sampleDelivered = new SampleDelivered();
        sampleDelivered.setNotifyEnabled(data.getNotifyEnabled_sampleDelivered());
        if (data.getNotifyEnabled_sampleDelivered().equals("1")){
            sampleDelivered.setNotifyTarget(data.getNotifyTarget_sampleDelivered());
        }
        ReportGenerated reportGenerated = new ReportGenerated();
        reportGenerated.setNotifyEnabled(data.getNotifyEnabled_reportGenerated());
        if (data.getNotifyEnabled_reportGenerated().equals("1")) {
            reportGenerated.setNotifyTarget(data.getNotifyTarget_reportGenerated());
        }
        ShortMessageCon shortMessageCon = new ShortMessageCon();
        shortMessageCon.setSampleSigned(sampleSigned);
        shortMessageCon.setSampleDelivered(sampleDelivered);
        shortMessageCon.setReportGenerated(reportGenerated);

        MaintainShortMessageRequest request = new MaintainShortMessageRequest();
        request.setConfigDetails(JsonUtils.toJson(shortMessageCon));
        request.setAgencyId(data.getAgencyId());
        gateway.request("/mgmt/shortMessage/modify", request);
    }

    @Override
    public List<ShortMessage> getShortMessage(Boolean agencyCustomized){
        QueryShortMessageRequest request = new QueryShortMessageRequest();
        request.setAgencyCustomized(agencyCustomized);
        ListResponse<com.todaysoft.ghealth.base.response.model.ShortMessage> response =
                gateway.request("/mgmt/shortMessage/getShortMessage", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.ShortMessage>>()
                {
                });
        List<ShortMessage> shortMessages = new ArrayList<ShortMessage>();
        if (null == response.getData())
        {
            return shortMessages;
        }

        return wrapper.wrap(response.getData());

    }


    @Override
    public void create(ShortMessageForm data)
    {
        SampleSigned sampleSigned = new SampleSigned();
        sampleSigned.setNotifyEnabled(data.getNotifyEnabled_sampleSigned());
        if (data.getNotifyEnabled_sampleSigned().equals("1")){
            sampleSigned.setNotifyTarget(data.getNotifyTarget_sampleSigned());
        }
        SampleDelivered sampleDelivered = new SampleDelivered();
        sampleDelivered.setNotifyEnabled(data.getNotifyEnabled_sampleDelivered());
        if (data.getNotifyEnabled_sampleDelivered().equals("1")){
            sampleDelivered.setNotifyTarget(data.getNotifyTarget_sampleDelivered());
        }
        ReportGenerated reportGenerated = new ReportGenerated();
        reportGenerated.setNotifyEnabled(data.getNotifyEnabled_reportGenerated());
        if (data.getNotifyEnabled_reportGenerated().equals("1")) {
            reportGenerated.setNotifyTarget(data.getNotifyTarget_reportGenerated());
        }
        ShortMessageCon shortMessageCon = new ShortMessageCon();
        shortMessageCon.setSampleSigned(sampleSigned);
        shortMessageCon.setSampleDelivered(sampleDelivered);
        shortMessageCon.setReportGenerated(reportGenerated);

        MaintainShortMessageRequest request = new MaintainShortMessageRequest();
        request.setConfigDetails(JsonUtils.toJson(shortMessageCon));
        request.setAgencyId(data.getAgencyId());
        gateway.request("/mgmt/shortMessage/create", request);
    }


    @Override
    public void delete(String id)
    {
        MaintainShortMessageRequest request = new MaintainShortMessageRequest();
        request.setId(id);
        gateway.request("/mgmt/shortMessage/delete", request);

    }

    @Override
    public ShortMessage get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.ShortMessage> response =
                gateway.request("/mgmt/shortMessage/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.ShortMessage>>()
                {
                });
        return wrapper.wrap(response.getData());
    }

    @Override
    public Boolean getByAgencyId(String agencyId)
    {
        QueryShortMessageRequest request = new QueryShortMessageRequest();
        request.setAgencyId(agencyId);
        ObjectResponse<Boolean> response =
                gateway.request("/mgmt/shortMessage/getByAgencyId", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
                {
                });
        if (null == response.getData())
        {
            return false;
        }
        return  response.getData().booleanValue();
    }


    @Override
    public ShortMessage getShortMessage(String agencyId)
    {
        QueryShortMessageRequest request = new QueryShortMessageRequest();
        request.setAgencyId(agencyId);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.ShortMessage> response =
                gateway.request("/mgmt/shortMessage/getMessage", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.ShortMessage>>()
                {
                });
        return wrapper.wrap(response.getData());
    }

    @Override
    public void create(SmsSend data)
    {
        MaintainSmsSendRequest request = new MaintainSmsSendRequest();
        BeanUtils.copyProperties(data, request, "createTime");
        request.setCreateTime(null == data.getCreateTime() ? null : String.valueOf(data.getCreateTime().getTime()));
        gateway.request("/mgmt/smsSend/create", request);
    }

}
