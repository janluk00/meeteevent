package com.janluk.meeteevent.event;

import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.event.dto.EventDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/event")
public class EventController {

    public final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.fetchAllEvents();

        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> getEventById(@PathVariable UUID id) {
        EventDTO event = eventService.fetchEventById(id);

        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @GetMapping(value = "/user/{user_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDTO>> getAllEventsByUserid(@PathVariable("user_id") UUID userId) {
        List<EventDTO> events = eventService.fetchAlLEventsByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @GetMapping(value = "/created_by/{user_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDTO>> getAllEventsCreatedByUserById(@PathVariable("user_id") UUID userId) {
        List<EventDTO> events = eventService.fetchAllEventsCreatedByUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody EventCreateRequest newEvent) {
        UUID eventId = eventService.createEvent(newEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventId.toString());
    }
}