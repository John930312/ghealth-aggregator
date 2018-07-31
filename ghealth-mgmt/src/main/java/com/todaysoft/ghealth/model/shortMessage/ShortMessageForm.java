package com.todaysoft.ghealth.model.shortMessage;

import java.util.List;

public class ShortMessageForm {
    private String agencyId;

    private String notifyEnabled_sampleSigned;

    private List<String> notifyTarget_sampleSigned;

    private String notifyEnabled_sampleDelivered;

    private List<String> notifyTarget_sampleDelivered;

    private String notifyEnabled_reportGenerated;

    private List<String> notifyTarget_reportGenerated;

    public String getNotifyEnabled_sampleSigned() {
        return notifyEnabled_sampleSigned;
    }

    public void setNotifyEnabled_sampleSigned(String notifyEnabled_sampleSigned) {
        this.notifyEnabled_sampleSigned = notifyEnabled_sampleSigned;
    }

    public List<String> getNotifyTarget_sampleSigned() {
        return notifyTarget_sampleSigned;
    }

    public void setNotifyTarget_sampleSigned(List<String> notifyTarget_sampleSigned) {
        this.notifyTarget_sampleSigned = notifyTarget_sampleSigned;
    }


    public String getNotifyEnabled_sampleDelivered() {
        return notifyEnabled_sampleDelivered;
    }

    public void setNotifyEnabled_sampleDelivered(String notifyEnabled_sampleDelivered) {
        this.notifyEnabled_sampleDelivered = notifyEnabled_sampleDelivered;
    }

    public List<String> getNotifyTarget_sampleDelivered() {
        return notifyTarget_sampleDelivered;
    }

    public void setNotifyTarget_sampleDelivered(List<String> notifyTarget_sampleDelivered) {
        this.notifyTarget_sampleDelivered = notifyTarget_sampleDelivered;
    }

    public String getNotifyEnabled_reportGenerated() {
        return notifyEnabled_reportGenerated;
    }

    public void setNotifyEnabled_reportGenerated(String notifyEnabled_reportGenerated) {
        this.notifyEnabled_reportGenerated = notifyEnabled_reportGenerated;
    }

    public List<String> getNotifyTarget_reportGenerated() {
        return notifyTarget_reportGenerated;
    }

    public void setNotifyTarget_reportGenerated(List<String> notifyTarget_reportGenerated) {
        this.notifyTarget_reportGenerated = notifyTarget_reportGenerated;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}
