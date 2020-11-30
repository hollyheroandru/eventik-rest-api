package com.egorhristoforov.eventikrestapi.dtos.requests.admin;

import javax.validation.constraints.FutureOrPresent;
import java.util.Date;

public class AdminEventUpdateRequest {
    private Double longitude;
    private Double latitude;
    private String apartment;
    private String title;
    private String description;
    private Long ownerId;
    private Long cityId;

    @FutureOrPresent
    private Date date;

    private Boolean isRegistrationRequired;

    public AdminEventUpdateRequest() {
    }

    public AdminEventUpdateRequest(Double longitude, Double latitude, String apartment, String title,
                                   String description, Date date, boolean isRegistrationRequired, Long ownerId, Long cityId) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.apartment = apartment;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isRegistrationRequired = isRegistrationRequired;
        this.ownerId = ownerId;
        this.cityId = cityId;
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

    public Boolean isRegistrationRequired() {
        return isRegistrationRequired;
    }

    public void setIsRegistrationRequired(boolean isRegistrationRequired) {
        this.isRegistrationRequired = isRegistrationRequired;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

