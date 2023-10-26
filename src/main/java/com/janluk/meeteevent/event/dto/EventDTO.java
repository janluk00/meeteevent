package com.janluk.meeteevent.event.dto;

import com.janluk.meeteevent.place.dto.PlaceDTO;

import java.util.Date;
import java.util.UUID;

public record EventDTO(

        UUID id,
        String name,
        Date date,
        String description,
        PlaceDTO place
) {}
