package com.egorhristoforov.eventikrestapi.dtos.responses.admin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AdminCountriesListResponse {
    private Long id;
    private String enName;
    private String ruName;
    private boolean isAddedByUser;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date createdDate;

    @JsonFormat(pattern = "yyyy.MM.dd 'T' HH:mm:ss Z")
    private Date lastModifiedDate;

    public AdminCountriesListResponse(Long id, String enName, String ruName,
                                      boolean isAddedByUser, Date createdDate, Date lastModifiedDate) {
        this.id = id;
        this.enName = enName;
        this.ruName = ruName;
        this.isAddedByUser = isAddedByUser;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public AdminCountriesListResponse() {
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
