package com.todaysoft.ghealth.agcy.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainAccountRequest extends SignatureTokenRequest {
    private String id;

    private String username;

    private String password;

    private Boolean enabled;

    private String name;

    private String phone;

    private String email;

    private String rolePlatForm;

    private String agencyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolePlatForm() {
        return rolePlatForm;
    }

    public void setRolePlatForm(String rolePlatForm) {
        this.rolePlatForm = rolePlatForm;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}
