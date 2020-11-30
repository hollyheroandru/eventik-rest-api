package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.egorhristoforov.eventikrestapi.models.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class AdminEventResponse {

    private Long id;
    private Double longitude;
    private Double latitude;
    private String apartment;
    private String title;
    private String description;
    private Long ownerId;
    private Long cityId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date date;

    private int countOfVisitors;
    private boolean registrationRequired;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date lastModifiedDate;

    public AdminEventResponse(Event event, int countOfVisitors) {
        id = event.getId();
        longitude = event.getLongitude();
        latitude = event.getLatitude();
        apartment = event.getApartment();
        title = event.getTitle();
        description = event.getDescription();
        ownerId = event.getOwner().getId();
        date = event.getDate();
        cityId = event.getCity().getId();
        this.countOfVisitors = countOfVisitors;
        registrationRequired = event.isRegistrationRequired();
        lastModifiedDate = event.getLastModifiedDate();
    }

}
