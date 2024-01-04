package com.janluk.meeteevent.event;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.janluk.meeteevent.place.Place;
import com.janluk.meeteevent.tag.Tag;
import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event")
@ToString
public class Event extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonBackReference
    private User createdBy;

    @ManyToMany(mappedBy = "events", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Tag> tags;

    public Event(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getEvents().add(this);
    }

    public void unsubscribeUser(User user) {
        this.users.remove(user);
        user.getEvents().remove(this);
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
        tags.forEach(tag -> tag.getEvents().add(this));
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getEvents().add(this);
    }

    public void deleteTag(Tag tag) {
        this.tags.remove(tag);
        tag.getEvents().remove(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Event event = (Event) o;
        return getId() != null && Objects.equals(getId(), event.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}