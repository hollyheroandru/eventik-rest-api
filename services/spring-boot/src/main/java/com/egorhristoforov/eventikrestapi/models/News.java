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

    @Column(name = "text")
    private String text;

    public News() {
    }

    public News(Long id, Event event, String text) {
        this.id = id;
        this.event = event;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String maintenance) {
        this.text = maintenance;
    }
}
