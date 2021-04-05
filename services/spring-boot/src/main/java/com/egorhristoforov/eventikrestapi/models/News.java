package com.egorhristoforov.eventikrestapi.models;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class News extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @Column(name = "maintenance")
    private String maintenance;

    public News() {
    }

    public News(Long id, Event event, String maintenance) {
        this.id = id;
        this.event = event;
        this.maintenance = maintenance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }
}
