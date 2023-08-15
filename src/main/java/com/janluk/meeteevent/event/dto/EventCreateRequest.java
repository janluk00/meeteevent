package com.janluk.meeteevent.event.dto;

import java.util.Date;
import java.util.UUID;

public record EventCreateRequest(
        String name,
        Date date,
        String description,
        String placeName,
        String placeAddress,
        UUID createdBy
) {}
