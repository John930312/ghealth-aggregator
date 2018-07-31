package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.Drug;
import com.todaysoft.ghealth.service.IDrugService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DrugWrapper {
    @Autowired
    private IDrugService service;

    public List<Drug> wrap(List<com.todaysoft.ghealth.mybatis.model.Drug> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Drug drug;
        List<Drug> drugs = new ArrayList<Drug>();
        for (com.todaysoft.ghealth.mybatis.model.Drug record : records)
        {
            drug = new Drug();
            wrap(record, drug);
            drugs.add(drug);
        }

        return drugs;
    }

    public Drug wrap(com.todaysoft.ghealth.mybatis.model.Drug record)
    {
        Drug drug = new Drug();
        wrap(record, drug);
        return drug;
    }

    private void wrap(com.todaysoft.ghealth.mybatis.model.Drug source, Drug target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");

        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
        target.setGeneName(service.getGeneName(source.getId()));
        target.setGeneId(service.getGeneId(source.getId()));
    }
}
