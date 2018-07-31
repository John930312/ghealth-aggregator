package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.Email;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class EmailWrapper {
    public List<Email> wrap(List<com.todaysoft.ghealth.mybatis.model.Email> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Email email;
        List<Email> emails = new ArrayList<Email>();
        for (com.todaysoft.ghealth.mybatis.model.Email record : records)
        {
            email = new Email();
            wrap(record, email);
            emails.add(email);
        }

        return emails;
    }

    public Email wrap(com.todaysoft.ghealth.mybatis.model.Email record)
    {
        Email email = new Email();
        wrap(record, email);
        return email;
    }

    private void wrap(com.todaysoft.ghealth.mybatis.model.Email source, Email target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");

        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
    }
}
