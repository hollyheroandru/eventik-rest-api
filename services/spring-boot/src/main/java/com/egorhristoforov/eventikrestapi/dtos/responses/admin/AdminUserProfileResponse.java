package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.UserRole;

import java.util.List;
import java.util.Set;

public class AdminUserProfileResponse {
    private String name;
    private String surname;
    private List<String> roles;

    public AdminUserProfileResponse(String name, String surname, List<String> roles) {
        this.name = name;
        this.surname = surname;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
