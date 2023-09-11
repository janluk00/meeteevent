package com.janluk.meeteevent.event;

import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.event.dto.EventDTO;
import com.janluk.meeteevent.event.mapper.EventMapper;
import com.janluk.meeteevent.place.Place;
import com.janluk.meeteevent.place.PlaceService;
import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.user.UserRepository;
import com.janluk.meeteevent.utils.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public List<EventDTO> fetchAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    public EventDTO fetchEventById(UUID id) {
        Event event = eventRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFound("Event with id: " + id + " not found"));

        return eventMapper.toEventDTO(event);
    }

    public List<EventDTO> fetchAlLEventsByUserId(UUID userId) {
        List<Event> events = eventRepository.findAllEventsByUserId(userId);

        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> fetchAllEventsCreatedByUserById(UUID userId) {
        List<Event> events = eventRepository.getAllEventsCreatedByUserById(userId);

        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UUID createEvent(EventCreateRequest request) {
        Event event = eventMapper.toEvent(request);

        eventRepository.save(event);

        return event.getId();
    }
}