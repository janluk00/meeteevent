package com.janluk.meeteevent.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Event> events;

    @OneToMany(targetEntity = Event.class, mappedBy = "createdBy")
    @JsonManagedReference
    private Set<Event> ownedEvents;

    public User(String login, String email, String password, String phone, String city, String name, String surname) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}