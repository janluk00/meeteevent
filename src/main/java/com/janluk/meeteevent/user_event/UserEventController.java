package com.janluk.meeteevent.user_event;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-event")
public class UserEventController {

    public final UserEventService userEventService;

    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserEvent>> getAllUserEvents() {
        List<UserEvent> events = userEventService.fetchAllUserEvents();

        return ResponseEntity.status(HttpStatus.OK).body(events);
    }
}