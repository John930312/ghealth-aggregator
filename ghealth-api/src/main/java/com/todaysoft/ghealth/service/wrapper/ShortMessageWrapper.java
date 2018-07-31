package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.ShortMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ShortMessageWrapper {

    public List<ShortMessage> wrap(List<com.todaysoft.ghealth.mybatis.model.ShortMessage> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        ShortMessage shortMessage;
        List<ShortMessage> shortMessages = new ArrayList<ShortMessage>();

        for (com.todaysoft.ghealth.mybatis.model.ShortMessage record : records)
        {
            shortMessage = new ShortMessage();
            wrap(record, shortMessage);
            shortMessages.add(shortMessage);
        }

        return shortMessages;
    }


    public ShortMessage wrap(com.todaysoft.ghealth.mybatis.model.ShortMessage record)
    {
        ShortMessage shortMessage = new ShortMessage();
        wrap(record, shortMessage);
        return shortMessage;
    }


    private void wrap(com.todaysoft.ghealth.mybatis.model.ShortMessage source, ShortMessage target)
    {

        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
    }
}
