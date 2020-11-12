package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class AdminUserCreateRequest {
    @Size(min = 2, max = 50, message = "Name length must be greater than or equal 6 and less than or equal to 50")
    private String name;

    @Size(min = 2, max = 50, message = "Name length must be greater than or equal 6 and less than or equal to 50")
    private String surname;

    @Size(min = 6, max = 50, message = "Email length must be greater than or equal 6 and less than or equal to 50")
    @Email
    private String email;

    @Size(min = 6, max = 50, message = "Password length must be greater than or equal 6 and less than or equal to 50")
    private String password;

    private Long[] rolesIds;

    public AdminUserCreateRequest(String name, String surname,
                                  String password, String email, Long[] rolesIds) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.rolesIds = rolesIds;
    }

    public AdminUserCreateRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long[] getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(Long[] roleId) {
        this.rolesIds = roleId;
    }
}
