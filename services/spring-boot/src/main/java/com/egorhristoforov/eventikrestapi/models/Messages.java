package com.egorhristoforov.eventikrestapi.models;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Messages extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @Column(name = "text")
    private String text;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Messages() {
    }

    public Messages(Long id, User user, Event event, String text, boolean isDeleted) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.text = text;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
