package com.egorhristoforov.eventikrestapi.dtos.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateRequest {
    @Size(min = 2, max = 50, message = "Name length must be greater than or equal 6 and less than or equal to 50")
    private String name;

    @Size(min = 2, max = 50, message = "Name length must be greater than or equal 6 and less than or equal to 50")
    private String Surname;

    @Size(min = 6, max = 50, message = "Password length must be greater than or equal 6 and less than or equal to 50")
    private String oldPassword;

    @Size(min = 6, max = 50, message = "Password length must be greater than or equal 6 and less than or equal to 50")
    private String newPassword;

    public UserUpdateRequest(String name, String surname, String oldPassword, String newPassword) {
        this.name = name;
        Surname = surname;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UserUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
