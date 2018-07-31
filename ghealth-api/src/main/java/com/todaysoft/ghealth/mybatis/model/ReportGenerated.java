package com.todaysoft.ghealth.mybatis.model;

import java.util.List;

public class ReportGenerated {
    private String notifyEnabled;

    private List<String> notifyTarget;

    public String getNotifyEnabled() {
        return notifyEnabled;
    }

    public void setNotifyEnabled(String notifyEnabled) {
        this.notifyEnabled = notifyEnabled;
    }

    public List<String> getNotifyTarget() {
        return notifyTarget;
    }

    public void setNotifyTarget(List<String> notifyTarget) {
        this.notifyTarget = notifyTarget;
    }
}
