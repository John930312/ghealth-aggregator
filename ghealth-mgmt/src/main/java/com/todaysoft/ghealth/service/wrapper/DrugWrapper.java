package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.model.drug.Drug;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class DrugWrapper {
    public List<Drug> wrap(List<com.todaysoft.ghealth.base.response.model.Drug> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Drug drug;
        List<Drug> drugs = new ArrayList<Drug>();
        for (com.todaysoft.ghealth.base.response.model.Drug record : records)
        {
            drug = new Drug();
            wrapRecord(record, drug);
            drugs.add(drug);
        }
        return drugs;
    }

    public Drug wrap(com.todaysoft.ghealth.base.response.model.Drug record)
    {
        if (null == record)
        {
            return null;
        }

        Drug drug = new Drug();
        wrapRecord(record, drug);
        return drug;
    }

    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Drug source, Drug target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
    }
}
