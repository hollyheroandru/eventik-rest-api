package com.egorhristoforov.eventikrestapi.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "ru_name")
    private String ruName;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @Column(name = "added_by_user")
    private boolean isAddedByUser = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "city")
    private Set<Event> events;

    public City(Long id, String enName, String ruName, Double longitude, Double latitude, Country country, boolean isAddedByUser) {
        this.id = id;
        this.enName = enName;
        this.ruName = ruName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.isAddedByUser = isAddedByUser;
    }

    public City() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isAddedByUser() {
        return isAddedByUser;
    }

    public void setAddedByUser(boolean addedByUser) {
        isAddedByUser = addedByUser;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
