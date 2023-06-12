package com.janluk.meeteevent.user.dto;

import java.util.UUID;

public record UserDTO (
        UUID id,
        String login,
        String email,
        String phone,
        String city,
        String name,
        String surname
) {}
