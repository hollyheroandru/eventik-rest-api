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
public class AdminCityResponse {
    private Long id;
    private String enName;
    private String ruName;
    private Double longitude;
    private Double latitude;
    private boolean isAddedByUser;
    private Long countryId;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date lastModifiedDate;
}
