package com.egorhristoforov.eventikrestapi.dtos.requests.field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConfigurationFieldRequest {
    @NotNull
    private String title;

    @NotNull
    private String placeholder;

    @NotNull
    @Positive
    private Long typeId;
}
