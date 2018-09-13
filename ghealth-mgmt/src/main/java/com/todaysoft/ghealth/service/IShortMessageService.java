package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.shortMessage.ShortMessage;
import com.todaysoft.ghealth.model.shortMessage.ShortMessageForm;
import com.todaysoft.ghealth.model.shortMessage.SmsSend;

import java.util.List;

public interface IShortMessageService {
    void modify(ShortMessageForm data);

    List<ShortMessage>  getShortMessage(Boolean agencyCustomized);

    void create(ShortMessageForm data);

    void delete(String id);

    ShortMessage get(String id);

    Boolean getByAgencyId(String agencyId);

    ShortMessage getShortMessage(String agencyId);

    void create(SmsSend data);
}
