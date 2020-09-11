package com.egorhristoforov.eventikrestapi.dtos.requests.user;

import javax.validation.constraints.*;

public class UserCreateRequest {
    @NotNull(message = "Email cannot be null")
    @Size(min = 6, max = 50, message = "Email length must be greater than or equal 6 and less than or equal to 50")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 50, message = "Password length must be greater than or equal 6 and less than or equal to 50")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name length must be greater than or equal 6 and less than or equal to 50")
    private String name;

    @NotNull(message = "Surname cannot be null")
    @Size(min = 2, max = 50, message = "Surname length must be greater than or equal 6 and less than or equal to 50")
    private String surname;

    public UserCreateRequest(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public UserCreateRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
