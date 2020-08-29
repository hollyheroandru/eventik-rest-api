package com.egorhristoforov.eventikrestapi.dtos.responses.location;

public class CitiesListResponse {
    private Long id;
    private String enName;
    private String ruName;
    private Double longitude;
    private Double latitude;

    public CitiesListResponse(Long id, String enName, String ruName, Double longitude, Double latitude) {
        this.id = id;
        this.enName = enName;
        this.ruName = ruName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public CitiesListResponse() {
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
}
