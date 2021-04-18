package com.egorhristoforov.eventikrestapi.dtos.requests.admin;


import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class AdminEventCreateRequest {
    @NotNull(message = "City id cannot be null")
    @Positive(message = "City id must be positive")
    private Long cityId;

    //@NotBlank
    private String apartment;

    @NotNull(message = "Owner id cannot be null")
    @Positive(message = "Owner id must be positive")
    private Long ownerId;

    @NotNull(message = "Title cannot be null")
    @NotBlank
    private String title;

    @NotNull(message = "Description cannot be null")
    @NotBlank
    private String description;

    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be future or present")
    private Date date;

    @NotNull(message = "Longitude cannot be null")
    private Double longitude;

    @NotNull(message = "Latitude cannot be null")
    private Double latitude;

    @NotNull(message = "IsRegistrationRequired cannot be null")
    private boolean isRegistrationRequired;

    public AdminEventCreateRequest() {
    }

    public AdminEventCreateRequest(Long cityId, String apartment, String title,
                                   String description, Date date, Double longitude,
                                   Double latitude, boolean isRegistrationRequired, Long ownerId) {
        this.cityId = cityId;
        this.apartment = apartment;
        this.title = title;
        this.description = description;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isRegistrationRequired = isRegistrationRequired;
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
        return isRegistrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.isRegistrationRequired = registrationRequired;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

}

