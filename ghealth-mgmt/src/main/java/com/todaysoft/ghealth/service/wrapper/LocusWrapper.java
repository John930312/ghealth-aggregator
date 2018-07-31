package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.dto.LocusDTO;
import com.todaysoft.ghealth.model.locus.Locus;

@Component
public class LocusWrapper
{
    public List<Locus> wrap(List<LocusDTO> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        Locus target;
        List<Locus> targets = new ArrayList<Locus>();
        
        for (LocusDTO source : sources)
        {
            target = new Locus();
            wrap(source, target);
            targets.add(target);
        }
        
        return targets;
    }
    
    public Locus wrap(LocusDTO source)
    {
        if (null == source)
        {
            return null;
        }
        
        Locus target = new Locus();
        wrap(source, target);
        return target;
    }
    
    private void wrap(LocusDTO source, Locus target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
    }
}
