package com.janluk.meeteevent.tag.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TagDTO(
        UUID id,
        String name
) {
}
