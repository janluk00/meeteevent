package com.janluk.meeteevent.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    // TODO: Add relationship
    @Column(name = "place_id")
    private UUID placeId;

    public Event(String name, Date date, String description, UUID placeId) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.placeId = placeId;
    }
}
