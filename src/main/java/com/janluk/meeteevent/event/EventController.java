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

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody EventCreateRequest newEvent) {
        UUID eventId = eventService.createEvent(newEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventId.toString());
    }
}