package com.egorhristoforov.eventikrestapi.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking extends Auditable {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private User visitor;

    public Booking() {
    }

    @Builder(toBuilder = true)
    public Booking(Long id, Event event, User visitor) {
        this.id = id;
        this.event = event;
        this.visitor = visitor;
    }

}
