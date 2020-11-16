package com.egorhristoforov.eventikrestapi.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<User> users;

    public UserRole() {

    }

    @Builder(toBuilder = true)
    public UserRole(Long id) {
        this.id = id;
    }

    @Builder(toBuilder = true)
    public UserRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public String getAuthority() {
        return "ROLE_" + getName();
    }
}
