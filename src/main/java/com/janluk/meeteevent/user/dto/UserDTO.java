package com.janluk.meeteevent.user.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDTO (
        UUID id,
        String login,
        String email,
        String phone,
        String city,
        String name,
        String surname
) {}
