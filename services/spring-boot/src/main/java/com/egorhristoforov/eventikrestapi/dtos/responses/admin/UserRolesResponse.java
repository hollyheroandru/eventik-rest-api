package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesResponse {
    private Long id;
    private String name;
}
