package com.todaysoft.ghealth.mybatis.searcher;

import javax.xml.ws.Service;

public class MessageSendSearcher {
    private String content;

    private String startTime;

    private String endTime;

    private Integer offset;

    private Integer limit;

    private String agencyId;

    private String title;

    private boolean nameExactMatches;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isNameExactMatches() {
        return nameExactMatches;
    }

    public void setNameExactMatches(boolean nameExactMatches) {
        this.nameExactMatches = nameExactMatches;
    }
}
