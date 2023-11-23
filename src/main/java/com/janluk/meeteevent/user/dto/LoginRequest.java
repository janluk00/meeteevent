package com.janluk.meeteevent.user.dto;

public record LoginRequest(
        String login,
        String password
) {
}
