package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.ShortMessage;

import java.util.List;

public interface ShortMessageMapper {
    int countIsExist();

    void create(ShortMessage data);

    void update(ShortMessage data);

    List<ShortMessage> getShortMessage(boolean agencyCustomized);


    ShortMessage get(String id);

    ShortMessage getByAgencyId(String agencyId);

    ShortMessage getMessage(String agencyId);
}
