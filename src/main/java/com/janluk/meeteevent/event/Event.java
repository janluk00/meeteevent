package com.janluk.meeteevent.event;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.janluk.meeteevent.place.Place;
import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.utils.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
@ToString
public class Event extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonBackReference
    private User createdBy;

    @ManyToMany(mappedBy = "events")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    public Event(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getEvents().add(this);
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