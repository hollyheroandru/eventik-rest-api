package com.egorhristoforov.eventikrestapi.dtos.requests.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PasswordRecoveryRequest {
    @NotNull(message = "Email cannot be null")
    @NotEmpty
    @Email
    private String email;

    public PasswordRecoveryRequest(String email) {
        this.email = email;
    }

    public PasswordRecoveryRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
