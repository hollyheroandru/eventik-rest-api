package com.egorhristoforov.eventikrestapi.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "ru_name")
    private String ruName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "country")
    private Set<City> cities;

    @Column(name = "added_by_user")
    private boolean isAddedByUser = false;

    public Country(Long id, String enName, String ruName, Set<City> cities, boolean isAddedByUser) {
        this.id = id;
        this.enName = enName;
        this.ruName = ruName;
        this.cities = cities;
        this.isAddedByUser = isAddedByUser;
    }

    public Country() {
    }

    public boolean isAddedByUser() {
        return isAddedByUser;
    }

    public void setAddedByUser(boolean addedByUser) {
        isAddedByUser = addedByUser;
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

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }
}