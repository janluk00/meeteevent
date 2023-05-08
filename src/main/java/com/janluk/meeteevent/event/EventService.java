package com.janluk.meeteevent.event;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> fetchAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> fetchEventById(UUID id) {
        return eventRepository.findEventById(id);
    }
}