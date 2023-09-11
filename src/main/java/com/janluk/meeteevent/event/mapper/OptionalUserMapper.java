package com.janluk.meeteevent.event.mapper;

import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.user.UserRepository;
import com.janluk.meeteevent.utils.exception.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OptionalUserMapper {
    private final UserRepository userRepository;

    @UserMapping
    public User fetchUser(UUID id) {
        return userRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFound("User with id: " + id + " not found"));
    }
}
