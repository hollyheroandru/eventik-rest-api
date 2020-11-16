package com.egorhristoforov.eventikrestapi.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends Auditable {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "en_name")
    private String enName;

    @Getter
    @Setter
    @Column(name = "ru_name")
    private String ruName;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "country")
    private Set<City> cities;

    @Getter
    @Setter
    @Column(name = "added_by_user")
    private boolean isAddedByUser = false;

    @Builder(toBuilder = true)
    public Country(Long id, String enName, String ruName, Set<City> cities, boolean isAddedByUser) {
        this.id = id;
        this.enName = enName;
        this.ruName = ruName;
        this.cities = cities;
        this.isAddedByUser = isAddedByUser;
    }

    public Country() {
    }
}
