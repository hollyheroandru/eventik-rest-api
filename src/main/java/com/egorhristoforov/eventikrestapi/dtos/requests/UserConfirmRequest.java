package com.egorhristoforov.eventikrestapi.dtos.requests;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class UserConfirmRequest {
    @NotNull(message = "Code cannot be null")
    @Size(min = 5, max = 5, message = "Code length must be equal to 5")
    private String code;

    public UserConfirmRequest() {

    }

    public UserConfirmRequest(@NotNull(message = "Code cannot be null") @Size(min = 5, max = 5, message = "Code length must be equal to 5") String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
