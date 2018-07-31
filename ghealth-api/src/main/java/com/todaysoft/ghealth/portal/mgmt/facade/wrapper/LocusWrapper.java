package com.todaysoft.ghealth.portal.mgmt.facade.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.dto.LocusDTO;
import com.todaysoft.ghealth.mybatis.model.Locus;
import com.todaysoft.ghealth.service.wrapper.Wrapper;

@Component
public class LocusWrapper implements Wrapper<Locus, LocusDTO>
{
    @Override
    public List<LocusDTO> wrap(List<Locus> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        LocusDTO target;
        List<LocusDTO> targets = new ArrayList<LocusDTO>();
        
        for (Locus source : sources)
        {
            target = new LocusDTO();
            wrap(source, target);
            targets.add(target);
        }
        
        return targets;
    }
    
    public LocusDTO wrap(Locus source)
    {
        if (null == source)
        {
            return null;
        }
        
        LocusDTO target = new LocusDTO();
        wrap(source, target);
        return target;
    }
    
    private void wrap(Locus source, LocusDTO target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
    }
}
