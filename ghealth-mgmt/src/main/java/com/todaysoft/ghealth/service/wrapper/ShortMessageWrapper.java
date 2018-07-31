package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.document.generate.sdk.utils.JsonUtils;
import com.todaysoft.ghealth.model.shortMessage.ShortMessage;
import com.todaysoft.ghealth.model.shortMessage.ShortMessageCon;
import com.todaysoft.ghealth.service.IAgencyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ShortMessageWrapper
{
    
    @Autowired
    private IAgencyService service;
    
    public List<ShortMessage> wrap(List<com.todaysoft.ghealth.base.response.model.ShortMessage> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        ShortMessage shortMessage;
        List<ShortMessage> shortMessages = new ArrayList<ShortMessage>();
        for (com.todaysoft.ghealth.base.response.model.ShortMessage record : records)
        {
            shortMessage = new ShortMessage();
            wrapRecord(record, shortMessage);
            shortMessages.add(shortMessage);
        }
        return shortMessages;
    }
    
    public ShortMessage wrap(com.todaysoft.ghealth.base.response.model.ShortMessage record)
    {
        if (null == record)
        {
            return null;
        }
        
        ShortMessage shortMessage = new ShortMessage();
        wrapRecord(record, shortMessage);
        return shortMessage;
    }
    
    public void wrapRecord(com.todaysoft.ghealth.base.response.model.ShortMessage source, ShortMessage target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
        if (!source.getConfigDetails().isEmpty())
        {
            ShortMessageCon data = JsonUtils.fromJson(source.getConfigDetails(), ShortMessageCon.class);
            target.setShortMessageCon(data);
        }
        if (source.getAgencyId() != null)
        {
            target.setAgencyName(service.get(source.getAgencyId()).getName());
        }
    }
}
