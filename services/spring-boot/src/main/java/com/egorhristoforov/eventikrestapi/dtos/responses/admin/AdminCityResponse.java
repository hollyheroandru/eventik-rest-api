package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.City;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
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

    public AdminCityResponse(City city) {
        id = city.getId();
        enName = city.getEnName();
        ruName = city.getRuName();
        longitude = city.getLongitude();
        latitude = city.getLatitude();
        isAddedByUser = city.isAddedByUser();
        countryId = city.getCountry().getId();
        createdDate = city.getCreatedDate();
        lastModifiedDate = city.getLastModifiedDate();
    }
}
