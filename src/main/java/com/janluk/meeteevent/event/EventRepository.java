package com.janluk.meeteevent.event;

import com.janluk.meeteevent.utils.base.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends BaseEntityRepository<Event, UUID> {
}