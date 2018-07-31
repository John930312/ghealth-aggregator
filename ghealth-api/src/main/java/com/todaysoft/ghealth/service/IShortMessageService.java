package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.ShortMessage;

import java.util.List;

public interface IShortMessageService {
    int countIsExist();

    void create(ShortMessage data);

    void modify(ShortMessage data);

    List<ShortMessage> getShortMessage(boolean agencyCustomized);

    ShortMessage get(String id);

    ShortMessage getByAgencyId(String agencyId);

    Boolean isUnique(String agencyId);

    ShortMessage getMessage(String agencyId);
}
