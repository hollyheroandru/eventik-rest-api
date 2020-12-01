package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminCityUpdateRequest {
    private String enName;
    private String ruName;
    private Long countryId;
    private boolean isAddedByUser = false;
}
