package com.janluk.meeteevent.place.dto;

import java.util.UUID;

public record PlaceDTO(
        UUID id,
        String name,
        String address
) {}
