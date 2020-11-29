package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import com.egorhristoforov.eventikrestapi.models.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminCityCreateRequest {
    @NotNull
    private String enName;

    @NotNull
    private String ruName;

    @NotNull
    private Long countryId;

    private boolean isAddedByUser = false;
}
