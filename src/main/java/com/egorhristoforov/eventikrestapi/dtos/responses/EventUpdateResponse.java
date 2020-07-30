package com.egorhristoforov.eventikrestapi.dtos.responses;

import java.util.Date;

public class EventUpdateResponse {
    private Long id;
    private Double longitude;
    private Double latitude;
    private String apartment;
    private String title;
    private String description;
    private Date date;
    private int countOfVisitors;
    private boolean registrationRequired;
    private Date lastModifiedDate;

    public EventUpdateResponse() {
    }

    public EventUpdateResponse(Long id, Double longitude, Double latitude, String apartment,
                               String title, String description, Date date, int countOfVisitors,
                               boolean registrationRequired, Date lastModifiedDate) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.apartment = apartment;
        this.title = title;
        this.description = description;
        this.date = date;
        this.countOfVisitors = countOfVisitors;
        this.registrationRequired = registrationRequired;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getCountOfVisitors() {
        return countOfVisitors;
    }

    public void setCountOfVisitors(int countOfVisitors) {
        this.countOfVisitors = countOfVisitors;
    }

    public boolean isRegistrationRequired() {
        return registrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        this.registrationRequired = registrationRequired;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
