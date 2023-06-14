package com.janluk.meeteevent.event;

import com.janluk.meeteevent.place.Place;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "place_id")
    private Place place;

    public Event(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}