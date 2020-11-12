package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import javax.validation.constraints.NotNull;

public class AdminCreateCountryRequest {

    @NotNull
    private String enName;

    @NotNull
    private String ruName;

    public AdminCreateCountryRequest(String enName, String ruName) {
        this.enName = enName;
        this.ruName = ruName;
    }

    public AdminCreateCountryRequest() {
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
