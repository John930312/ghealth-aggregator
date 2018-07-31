package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.ShortMessage;
import com.todaysoft.ghealth.mgmt.request.MaintainShortMessageRequest;
import com.todaysoft.ghealth.mgmt.request.QueryShortMessageRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IShortMessageService;
import com.todaysoft.ghealth.service.wrapper.ShortMessageWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShortMessageFacade {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IShortMessageService service;

    @Autowired
    private ShortMessageWrapper wrapper;

    public void modify(MaintainShortMessageRequest request){
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }
        com.todaysoft.ghealth.mybatis.model.ShortMessage shortMessage = new com.todaysoft.ghealth.mybatis.model.ShortMessage();
        if(request.getAgencyId()!=null){
            shortMessage = service.getByAgencyId(request.getAgencyId());
            shortMessage.setConfigDetails(request.getConfigDetails());
            shortMessage.setUpdatorName(account.getName());
            shortMessage.setUpdateTime(new Date());
            service.modify(shortMessage);
        }else {
            if (service.countIsExist()==0){
                shortMessage.setId(IdGen.uuid());
                shortMessage.setConfigDetails(request.getConfigDetails());
                shortMessage.setAgencyCustomized(false);
                shortMessage.setDeleted(false);
                shortMessage.setCreatorName(account.getName());
                shortMessage.setCreateTime(new Date());
                service.create(shortMessage);
            }else {
                shortMessage=service.getShortMessage(false).get(0);
                shortMessage.setConfigDetails(request.getConfigDetails());
                shortMessage.setUpdatorName(account.getName());
                shortMessage.setUpdateTime(new Date());
                service.modify(shortMessage);
            }
        }

    }


    public void create(MaintainShortMessageRequest request){
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }
        com.todaysoft.ghealth.mybatis.model.ShortMessage shortMessage = new com.todaysoft.ghealth.mybatis.model.ShortMessage();
            shortMessage.setId(IdGen.uuid());
            shortMessage.setConfigDetails(request.getConfigDetails());
            shortMessage.setAgencyId(request.getAgencyId());
            shortMessage.setAgencyCustomized(true);
            shortMessage.setDeleted(false);
            shortMessage.setCreatorName(account.getName());
            shortMessage.setCreateTime(new Date());
            service.create(shortMessage);
    }

    public ListResponse<ShortMessage> getShortMessage(QueryShortMessageRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.ShortMessage shortMessage = new com.todaysoft.ghealth.mybatis.model.ShortMessage();
        shortMessage.setAgencyCustomized(request.getAgencyCustomized());
        return new ListResponse<ShortMessage>(wrapper.wrap(service.getShortMessage(shortMessage.getAgencyCustomized())));
    }


    public void delete(MaintainShortMessageRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account)
        {
            throw new IllegalStateException();
        }

        com.todaysoft.ghealth.mybatis.model.ShortMessage shortMessage = new com.todaysoft.ghealth.mybatis.model.ShortMessage();
        shortMessage.setId(request.getId());
        shortMessage.setDeleted(true);
        shortMessage.setDeletorName(account.getName());
        shortMessage.setDeleteTime(new Date());
        service.modify(shortMessage);
    }


    public ObjectResponse<ShortMessage> get(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.ShortMessage shortMessage = service.get(request.getId());
        ShortMessage data = wrapper.wrap(shortMessage);
        return new ObjectResponse<ShortMessage>(data);
    }

    public ObjectResponse<Boolean> getByAgencyId(QueryShortMessageRequest request)
    {
        boolean unique = service.isUnique(request.getAgencyId());


        return new ObjectResponse<Boolean>(unique);
    }


    public ObjectResponse<ShortMessage> getMessage(QueryShortMessageRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.ShortMessage shortMessage = service.getMessage(request.getAgencyId());
        ShortMessage data = wrapper.wrap(shortMessage);
        return new ObjectResponse<ShortMessage>(data);
    }
}
