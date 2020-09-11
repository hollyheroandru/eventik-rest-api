package com.egorhristoforov.eventikrestapi.dtos.responses.user;

public class UserProfileResponse {
    private String name;
    private String surname;

    public UserProfileResponse(String name, String surname) {
        this.name = name;
        this.surname = surname;
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
