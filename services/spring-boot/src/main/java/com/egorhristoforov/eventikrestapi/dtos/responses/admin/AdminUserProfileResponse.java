package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.User;
import com.egorhristoforov.eventikrestapi.models.UserRole;
import lombok.Getter;

import javax.validation.constraints.Email;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AdminUserProfileResponse {
    private String name;
    private String surname;

    @Email
    private String email;

    private List<UserRolesResponse> roles;

    public AdminUserProfileResponse(User user, List<UserRolesResponse> roles) {
        name = user.getName();
        surname = user.getSurname();
        email = user.getEmail();
        this.roles = roles;
    }
}
