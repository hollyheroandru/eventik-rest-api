package com.egorhristoforov.eventikrestapi.dtos.requests.admin;


import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class AdminEventCreateRequest {
    @NotNull
    @Positive
    private Long cityId;

    //@NotBlank
    private String apartment;

    @NotNull
    @Positive
    private Long ownerId;

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

    public AdminEventCreateRequest() {
    }

    public AdminEventCreateRequest(Long cityId, String apartment, String title,
                                   String description, Date date, Double longitude,
                                   Double latitude, boolean registrationRequired, Long ownerId) {
        this.cityId = cityId;
        this.apartment = apartment;
        this.title = title;
        this.description = description;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.registrationRequired = registrationRequired;
        this.ownerId = ownerId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

}

