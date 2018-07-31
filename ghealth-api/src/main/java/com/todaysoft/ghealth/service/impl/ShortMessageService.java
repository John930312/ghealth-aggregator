package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.ShortMessageMapper;
import com.todaysoft.ghealth.mybatis.model.ShortMessage;
import com.todaysoft.ghealth.service.IShortMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortMessageService implements IShortMessageService{

    @Autowired(required = false)
    private ShortMessageMapper mapper;

    @Override
    public int countIsExist()
    {
        return mapper.countIsExist();
    }
    @Override
    public void create(ShortMessage data){
         mapper.create(data);
    }
    @Override
    public void modify(ShortMessage data){
        mapper.update(data);
    }
    @Override
    public List<ShortMessage> getShortMessage(boolean agencyCustomized )
    {
        return mapper.getShortMessage(agencyCustomized);
    }

    @Override
    public ShortMessage get(String id){
        return mapper.get(id);
    }
    @Override
    public ShortMessage getByAgencyId(String agencyId){
        return mapper.getByAgencyId(agencyId);
    }


    @Override
    public Boolean isUnique(String agencyId){
      if (mapper.getByAgencyId(agencyId)==null){
          return false;
      }else {
          return  true;
      }
    }

    @Override
    public ShortMessage getMessage(String agencyId){
        return mapper.getMessage(agencyId);
    }
}
