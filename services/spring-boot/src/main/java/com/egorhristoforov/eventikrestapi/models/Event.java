package com.egorhristoforov.eventikrestapi.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event extends Auditable {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

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
    private City city;

    @Getter
    @Setter
    @Column(name = "apartment")
    private String apartment;

    @Getter
    @Setter
    @Column(name = "title")
    private String title;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Column(name = "date")
    private Date date;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
    private Set<Booking> bookings;

    @Getter
    @Setter
    @Column(name = "registration_required")
    private boolean isRegistrationRequired;

    public Event() {
    }

    @Builder(toBuilder = true)
    public Event(Long id, User owner, Double longitude, Double latitude, City city, String apartment,
                 String title, String description, Date date, Set<Booking> bookings,
                 boolean isRegistrationRequired) {
        this.id = id;
        this.owner = owner;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.apartment = apartment;
        this.title = title;
        this.description = description;
        this.date = date;
        this.bookings = bookings;
        this.isRegistrationRequired = isRegistrationRequired;
    }
}
