package com.egorhristoforov.eventikrestapi.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User extends Auditable implements UserDetails {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "surname")
    private String surname;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> roles;

    @Getter
    @Setter
    @Column(name = "email_confirmation_code")
    private String emailConfirmationCode;

    @Getter
    @Setter
    @Column(name = "password_confirmation_code")
    private String passwordConfirmationCode;

    @Getter
    @Setter
    @Column(name = "is_activated")
    private Boolean isActivated = false;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Event> createdEvents;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "visitor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Booking> bookings;

    public User() {
        super();
    }

    @Builder(toBuilder = true)
    public User(String email, String password, String name, String surname, String emailConfirmationCode,
                String passwordConfirmationCode,
                Boolean isActivated) {
        super();

        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.emailConfirmationCode = emailConfirmationCode;
        this.passwordConfirmationCode = passwordConfirmationCode;
        this.isActivated = isActivated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActivated;
    }
}
