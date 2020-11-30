package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.User;
import lombok.Getter;

@Getter
public class UsersListResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;

    public UsersListResponse(User user) {
        id = user.getId();
        email = user.getEmail();
        name = user.getName();
        surname = user.getSurname();
    }
}
