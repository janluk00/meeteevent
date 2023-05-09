package com.janluk.meeteevent.user_event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_event")
public class UserEvent {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "event_id")
    private UUID eventId;

    // TODO: Add relationship Many2Many

    public UserEvent(UUID userId, UUID eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }
}
