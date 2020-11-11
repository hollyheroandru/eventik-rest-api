package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.UserRole;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AdminUserProfileResponse {
    private String name;
    private String surname;

    @Email
    private String email;

    private HashMap<Long, String> roles;

    public AdminUserProfileResponse(String name, String surname, String email, HashMap<Long, String> roles) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roles = roles;
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

    public HashMap<Long, String> getRoles() {
        return roles;
    }

    public void setRoles(HashMap<Long, String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
