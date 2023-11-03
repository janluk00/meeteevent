package com.janluk.meeteevent.event;

import com.janluk.meeteevent.utils.base.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface EventRepository extends BaseEntityRepository<Event, UUID> {

    @Query(
            value = "select event.* from event join user_event on event.id = user_event.event_id " +
                    "where user_event.user_id = ?1",
            nativeQuery = true
    )
    List<Event> findAllEventsByUserId(UUID userId);

    @Query(
            value = "select event.* from event where event.created_by = ?1",
            nativeQuery = true
    )
    List<Event> getAllEventsCreatedByUserById(UUID userId);

    @Query(
            value = "select event.* from event left join user_event on event.id = user_event.event_id and " +
                    "user_event.user_id = ?1 where user_event.event_id is null and event.date > current_timestamp",
            nativeQuery = true
    )
    List<Event> getAllUnassignedEventsByUserId(UUID userId);

    @Query(
            value = "select event.* from event join user_event on event.id = user_event.event_id " +
                    "where user_event.user_id = ?1 and event.date < current_timestamp",
            nativeQuery = true
    )
    List<Event> findAllFinishedEventsByUserId(UUID userId);
}