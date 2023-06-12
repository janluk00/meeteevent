package com.janluk.meeteevent.event;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    public final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.fetchAllEvents();

        return ResponseEntity.status(HttpStatus.OK).body(events);
    }
}