package com.egorhristoforov.eventikrestapi.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    @Column(name = "apartment")
    private String apartment;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
    private Set<Booking> bookings;

    @Column(name = "registration_required")
    private boolean isRegistrationRequired;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> blockedUsers;

    public Event() {
    }

    public Event(Long id, User owner, Double longitude, Double latitude, City city, String apartment,
                 String title, String description, Date date, Set<Booking> bookings,
                 boolean isRegistrationRequired, Set<User> blockedUsers) {
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
        this.blockedUsers = blockedUsers;
    }

    public Set<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public boolean isRegistrationRequired() {
        return isRegistrationRequired;
    }

    public void setRegistrationRequired(boolean registrationRequired) {
        isRegistrationRequired = registrationRequired;
    }
}