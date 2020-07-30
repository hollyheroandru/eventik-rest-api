package com.egorhristoforov.eventikrestapi.dtos.responses.event;

import java.util.Date;

public class EventsListResponse {
    private Long id;
    private Double longitude;
    private Double latitude;
    private String apartment;
    private String title;
    private Date date;
    private Date lastModifiedDate;

    public EventsListResponse() {
    }

    public EventsListResponse(Long id, Double longitude, Double latitude, String apartment,
                              String title, Date date, Date lastModifiedDate) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.apartment = apartment;
        this.title = title;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
