package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventResponse {
    private Long id;
    private Double longitude;
    private Double latitude;
    private String apartment;
    private String title;
    private String description;
    private Long ownerId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date date;

    private int countOfVisitors;
    private boolean registrationRequired;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date lastModifiedDate;
}
