package com.egorhristoforov.eventikrestapi.models;

import javax.persistence.*;

@Entity
@Table(name = "field")
public class Field extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "placeholder")
    private String placeholder;

    @OneToOne(fetch = FetchType.LAZY)
    private FieldType type;

    public Field() {
    }

    public Field(Long id, String title, String placeholder, FieldType type) {
        this.id = id;
        this.title = title;
        this.placeholder = placeholder;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }
}
