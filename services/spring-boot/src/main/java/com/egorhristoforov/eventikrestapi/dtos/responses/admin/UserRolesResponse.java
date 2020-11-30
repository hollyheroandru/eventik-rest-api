package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.UserRole;
import lombok.*;

@Getter
public class UserRolesResponse {
    private Long id;
    private String name;

    public UserRolesResponse(UserRole userRole) {
        id = userRole.getId();
        name = userRole.getName();
    }
}
