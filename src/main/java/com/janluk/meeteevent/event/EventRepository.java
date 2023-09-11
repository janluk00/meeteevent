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
}