package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.Country;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class AdminCountriesListResponse {
    private Long id;
    private String enName;
    private String ruName;
    private boolean isAddedByUser;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date lastModifiedDate;

    public AdminCountriesListResponse(Country country) {
        id = country.getId();
        enName = country.getEnName();
        ruName = country.getRuName();
        isAddedByUser = country.isAddedByUser();
        createdDate = country.getCreatedDate();
        lastModifiedDate = country.getLastModifiedDate();
    }
}
