package com.egorhristoforov.eventikrestapi.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "black_list")
public class BlackList extends Auditable{
    @Id
    @OneToOne(optional = false, mappedBy = "id")
    private Event event;

    @OneToMany()
    private Set<User> users;

    public BlackList() {
    }

    public BlackList(Event event, Set<User> users) {
        this.event = event;
        this.users = users;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
