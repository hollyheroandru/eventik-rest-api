package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import javax.validation.constraints.NotNull;

public class AdminCountryUpdateRequest {
    private String enName;
    private String ruName;
    private boolean isAddedByUser;

    public AdminCountryUpdateRequest(String enName, String ruName, boolean isAddedByUser) {
        this.enName = enName;
        this.ruName = ruName;
        this.isAddedByUser = isAddedByUser;
    }

    public AdminCountryUpdateRequest() {
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public boolean isAddedByUser() {
        return isAddedByUser;
    }

    public void setAddedByUser(boolean addedByUser) {
        isAddedByUser = addedByUser;
    }
}
