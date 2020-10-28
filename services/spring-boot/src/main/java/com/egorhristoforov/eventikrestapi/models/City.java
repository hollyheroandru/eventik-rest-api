package com.egorhristoforov.eventikrestapi.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cities")
public class City extends Auditable {
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
    @Column(name = "longitude")
    private Double longitude;

    @Getter
    @Setter
    @Column(name = "latitude")
    private Double latitude;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @Getter
    @Setter
    @Column(name = "added_by_user")
    private boolean isAddedByUser = false;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "city")
    private Set<Event> events;

    @Builder(toBuilder = true)
    public City(Long id, String enName, String ruName, Double longitude, Double latitude,
                Country country, boolean isAddedByUser) {
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
}
