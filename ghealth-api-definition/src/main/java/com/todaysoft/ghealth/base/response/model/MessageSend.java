package com.todaysoft.ghealth.base.response.model;


import java.util.List;

public class MessageSend {
    private String id;

    private String agencyId;

    private String title;

    private String content;

    private String address;

    private Long pushTime;

    private List<String> agencyName;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPushTime() {
        return pushTime;
    }

    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }

    public List<String> getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(List<String> agencyName) {
        this.agencyName = agencyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
