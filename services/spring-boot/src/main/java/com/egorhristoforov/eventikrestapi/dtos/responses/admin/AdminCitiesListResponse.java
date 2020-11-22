package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AdminCitiesListResponse {
    private Long id;
    private String enName;
    private String ruName;
    private Double longitude;
    private Double latitude;
    private boolean isAddedByUser;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date lastModifiedDate;

    public AdminCitiesListResponse() {
    }

    public AdminCitiesListResponse(Long id, String enName, String ruName,
                                   Double longitude, Double latitude, boolean isAddedByUser, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.enName = enName;
        this.ruName = ruName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isAddedByUser = isAddedByUser;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
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

    public boolean isAddedByUser() {
        return isAddedByUser;
    }

    public void setAddedByUser(boolean addedByUser) {
        isAddedByUser = addedByUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
