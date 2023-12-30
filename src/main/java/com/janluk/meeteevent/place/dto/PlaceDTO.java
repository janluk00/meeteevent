package com.janluk.meeteevent.place.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PlaceDTO(
        UUID id,
        String name,
        String address
) {}
