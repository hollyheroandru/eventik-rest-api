package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class AdminUserUpdateRequest {
    @Size(min = 2, max = 50, message = "Name length must be greater than or equal 6 and less than or equal to 50")
    private String name;

    @Size(min = 2, max = 50, message = "Name length must be greater than or equal 6 and less than or equal to 50")
    private String surname;

    @Size(min = 6, max = 50, message = "Email length must be greater than or equal 6 and less than or equal to 50")
    @Email
    private String email;

    @Size(min = 6, max = 50, message = "Password length must be greater than or equal 6 and less than or equal to 50")
    private String newPassword;

    public AdminUserUpdateRequest(String name, String surname, String oldPassword,
                                  String newPassword, String email) {
        this.name = name;
        this.surname = surname;
        this.newPassword = newPassword;
        this.email = email;
    }

    public AdminUserUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}