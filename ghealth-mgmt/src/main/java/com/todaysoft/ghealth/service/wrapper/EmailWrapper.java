package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.Cancer;
import com.todaysoft.ghealth.model.email.Email;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class EmailWrapper {
    public List<Email> wrap(List<com.todaysoft.ghealth.base.response.model.Email> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Email email;
        List<Email> emails = new ArrayList<Email>();
        for (com.todaysoft.ghealth.base.response.model.Email record : records)
        {
            email = new Email();
            wrapRecord(record, email);
            emails.add(email);
        }
        return emails;
    }

    public Email wrap(com.todaysoft.ghealth.base.response.model.Email record)
    {
        if (null == record)
        {
            return null;
        }

        Email email = new Email();
        wrapRecord(record, email);
        return email;
    }

    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Email source, Email target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
    }
}
