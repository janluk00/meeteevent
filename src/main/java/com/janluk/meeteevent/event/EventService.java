package com.janluk.meeteevent.event;

import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.event.dto.EventDTO;
import com.janluk.meeteevent.event.dto.EventWithUsersDTO;
import com.janluk.meeteevent.event.mapper.EventMapper;
import com.janluk.meeteevent.tag.dto.TagDTO;
import com.janluk.meeteevent.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    public EventWithUsersDTO fetchEventById(UUID id) {
        Event event = eventRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFound("Event with id: " + id + " not found"));

        return eventMapper.toEventWithUsersDTO(event);
    }

    public List<EventDTO> fetchAllEventsByUserId(UUID userId) {
        List<Event> events = eventRepository.findAllEventsByUserId(userId);

        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> fetchAllEventsCreatedByUserById(UUID userId) {
        List<Event> events = eventRepository.findAllEventsCreatedByUserById(userId).stream()
                .sorted(Comparator.comparing(Event::getDate).reversed())
                .toList();

        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> fetchAllUnassignedEventsByUserId(UUID userId) {
        List<Event> events = eventRepository.findAllUnassignedEventsByUserId(userId);

        return events.stream()
                .map(eventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    public List<EventDTO> fetchAllRecommendedEventsByUserId(UUID userId) {
        List<EventDTO> userEvents = fetchAllEventsByUserId(userId);
        List<EventDTO> unassignedEvents = fetchAllUnassignedEventsByUserId(userId);

        Map<TagDTO, Long> userTagOccurrences = userEvents.stream()
                .flatMap(event -> event.tags().stream())
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));

        List<EventDTO> recommendedEvents = unassignedEvents.stream()
                .collect(Collectors.toMap(
                        event -> event, event -> calculateCompatibility(event, userTagOccurrences)))
                .entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .map(Map.Entry::getKey)
                .toList();

        return recommendedEvents;
    }

    private int calculateCompatibility(EventDTO event, Map<TagDTO, Long> occurencesMap) {
        int compatibility = 0;

        for (TagDTO tag : event.tags()) {
            if (occurencesMap.containsKey(tag))
                compatibility += occurencesMap.get(tag);
        }

        return compatibility;
    }

    public List<EventDTO> fetchAllFinishedEventsByUserId(UUID userId) {
        List<Event> events = eventRepository.findAllFinishedEventsByUserId(userId);

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