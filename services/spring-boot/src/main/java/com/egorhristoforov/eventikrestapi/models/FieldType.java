package com.egorhristoforov.eventikrestapi.models;

import javax.persistence.*;

@Entity
@Table(name = "field_type")
public class FieldType extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_type")
    private String type;

    public FieldType() {
    }

    public FieldType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
