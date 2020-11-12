package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import javax.validation.constraints.NotNull;

public class AdminCountryCreateRequest {

    @NotNull
    private String enName;

    @NotNull
    private String ruName;

    public AdminCountryCreateRequest(String enName, String ruName) {
        this.enName = enName;
        this.ruName = ruName;
    }

    public AdminCountryCreateRequest() {
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
}
