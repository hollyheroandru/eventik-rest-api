package com.egorhristoforov.eventikrestapi.dtos.requests.event;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class EventCreateRequest {
    @NotNull
    @Positive
    private Long cityId;

    @NotBlank
    @NotNull
    private String Apartment;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @FutureOrPresent
    private Date date;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotNull
    private boolean registrationRequired;

    public EventCreateRequest() {
    }

    public EventCreateRequest(Long cityId, String apartment, String title, String description, Date date, Double longitude, Double latitude, boolean registrationRequired) {
        this.cityId = cityId;
        Apartment = apartment;
        this.title = title;
        this.description = description;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.registrationRequired = registrationRequired;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getApartment() {
        return Apartment;
    }

    public void setApartment(String apartment) {
        Apartment = apartment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public boolean isRegistrationRequired() {
        return registrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }
}
