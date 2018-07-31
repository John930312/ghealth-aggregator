package com.todaysoft.ghealth.model.messageSend;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class MessageSend {
    private String id;

    private String agencyId;

    private String title;

    private String content;

    private String address;

    private Date pushTime;

    private List<String> agencyName;

    private byte[] fileByte;

    private String path;

    private String url;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public List<String> getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(List<String> agencyName) {
        this.agencyName = agencyName;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
