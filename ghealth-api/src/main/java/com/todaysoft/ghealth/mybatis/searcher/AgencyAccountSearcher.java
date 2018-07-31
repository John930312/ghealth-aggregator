package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Set;

public class AgencyAccountSearcher {
    private String username;

    private String agencyId;

    private boolean primaryAccount;

    private boolean usernameExactMatches;

    private Set<String> excludeKeys;

    private String phone;

    private String name;

    private Integer offset;

    private Integer limit;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public boolean isPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(boolean primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public boolean isUsernameExactMatches() {
        return usernameExactMatches;
    }

    public void setUsernameExactMatches(boolean usernameExactMatches) {
        this.usernameExactMatches = usernameExactMatches;
    }

    public Set<String> getExcludeKeys() {
        return excludeKeys;
    }

    public void setExcludeKeys(Set<String> excludeKeys) {
        this.excludeKeys = excludeKeys;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
