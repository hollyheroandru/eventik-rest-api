package com.egorhristoforov.eventikrestapi.dtos.responses.event;

import com.egorhristoforov.eventikrestapi.models.User;
import lombok.Getter;

@Getter
public class EventBlockedUserResponse {
    private Long id;
    private String name;
    private String surname;

    public EventBlockedUserResponse(User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
    }
}
