package com.egorhristoforov.eventikrestapi.dtos.responses.event;

import com.egorhristoforov.eventikrestapi.models.User;
import lombok.Getter;

@Getter
public class EventBlockedUsersListResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;

    public EventBlockedUsersListResponse(User user) {
        id = user.getId();
        email = user.getEmail();
        name = user.getName();
        surname = user.getSurname();
    }
}