package com.janluk.meeteevent.user_event;

import org.springframework.stereotype.Service;

import java.util.List;

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