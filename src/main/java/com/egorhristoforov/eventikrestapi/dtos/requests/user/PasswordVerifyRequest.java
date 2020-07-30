package com.egorhristoforov.eventikrestapi.dtos.requests.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordVerifyRequest {
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 5)
    private String code;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    public PasswordVerifyRequest(String email, String code, String password) {
        this.email = email;
        this.code = code;
        this.password = password;
    }

    public PasswordVerifyRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
