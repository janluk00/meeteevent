package com.janluk.meeteevent.event.dto;

import com.janluk.meeteevent.place.dto.PlaceCreateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventCreateRequest(

        @NotBlank(message = "Event name is required")
        @Size(
                min = 2,
                max = 48,
                message = "The name should be between 2 and 48 characters long"
        )
        String name,
        @NotNull(message = "Event date is required")
        Date date,
        @Size(
                max = 255,
                message = "The description cannot be longer than 255 characters"
        )
        String description,
        @NotNull
        PlaceCreateRequest place,
        @NotNull
        UUID createdBy,
        List<UUID> tags
) {}
