package com.egorhristoforov.eventikrestapi.dtos.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AuthRefreshRequest {
    @NotNull
    @NotEmpty
    private String refreshToken;

    public AuthRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AuthRefreshRequest() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
