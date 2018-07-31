package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.LocusDTO;
import com.todaysoft.ghealth.mgmt.request.MaintainLocusRequest;
import com.todaysoft.ghealth.mgmt.request.QueryLocusByNamesRequest;
import com.todaysoft.ghealth.mgmt.request.QueryLocusRequest;
import com.todaysoft.ghealth.model.locus.Locus;
import com.todaysoft.ghealth.model.locus.LocusSearcher;
import com.todaysoft.ghealth.service.ILocusService;
import com.todaysoft.ghealth.service.wrapper.LocusWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class LocusService implements ILocusService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private LocusWrapper wrapper;
    
    @Override
    public Pagination<Locus> pagination(LocusSearcher searcher, int pageNo, int pageSize)
    {
        QueryLocusRequest request = new QueryLocusRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        BeanUtils.copyProperties(searcher, request);
        
        PagerResponse<LocusDTO> response = gateway.request("/mgmt/locus/pager", request, new ParameterizedTypeReference<PagerResponse<LocusDTO>>()
        {
        });
        
        Pager<LocusDTO> pager = response.getData();
        Pagination<Locus> pagination = new Pagination<Locus>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public Map<String, Locus> getLocusAsNameMappings(List<String> names)
    {
        if (CollectionUtils.isEmpty(names))
        {
            return Collections.emptyMap();
        }
        
        Set<String> args = new HashSet<String>();
        
        for (String name : names)
        {
            if (!StringUtils.isEmpty(name))
            {
                args.add(name);
            }
        }
        
        if (CollectionUtils.isEmpty(args))
        {
            return Collections.emptyMap();
        }
        
        QueryLocusByNamesRequest request = new QueryLocusByNamesRequest();
        request.setNames(args);
        
        ListResponse<LocusDTO> response = gateway.request("/mgmt/locus/list/names", request, new ParameterizedTypeReference<ListResponse<LocusDTO>>()
        {
        });
        
        List<LocusDTO> records = response.getData();
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyMap();
        }
        
        Locus locus;
        Map<String, Locus> mappings = new HashMap<String, Locus>();
        
        for (LocusDTO record : records)
        {
            locus = new Locus();
            locus.setId(record.getId());
            locus.setName(record.getName());
            mappings.put(locus.getName(), locus);
        }
        
        return mappings;
    }
    
    @Override
    public Locus get(String id)
    {
        MaintainLocusRequest request = new MaintainLocusRequest();
        request.setId(id);
        
        ObjectResponse<LocusDTO> response = gateway.request("/mgmt/locus/get", request, new ParameterizedTypeReference<ObjectResponse<LocusDTO>>()
        {
        });
        
        return wrapper.wrap(response.getData());
    }
    
    @Override
    public void create(Locus data)
    {
        MaintainLocusRequest request = new MaintainLocusRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/locus/create", request);
    }
    
    @Override
    public void modify(Locus data)
    {
        MaintainLocusRequest request = new MaintainLocusRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/locus/modify", request);
    }
    
    @Override
    public void delete(String id)
    {
        MaintainLocusRequest request = new MaintainLocusRequest();
        request.setId(id);
        gateway.request("/mgmt/locus/delete", request);
    }
    
    @Override
    public boolean isNameUnique(String id, String name)
    {
        MaintainLocusRequest request = new MaintainLocusRequest();
        request.setId(id);
        request.setName(name);
        
        ObjectResponse<Boolean> response = gateway.request("/mgmt/locus/unique/name", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        
        if (null == response.getData())
        {
            return false;
        }
        
        return response.getData().booleanValue();
    }
}
