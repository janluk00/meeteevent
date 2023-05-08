package com.janluk.meeteevent.user_event;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.event.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserEventService {

    private final UserEventRepository userEventRepository;

    public UserEventService(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }

    public List<UserEvent> fetchAllUserEvents() {
        return userEventRepository.findAll();
    }
}