package com.janluk.meeteevent.place.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record PlaceCreateRequest(

        @NotBlank(message = "Event name is required")
        @Size(
                min = 2,
                max = 48,
                message = "The name should be between 2 and 48 characters long"
        )
        String name,
        @NotBlank(message = "Event name is required")
        @Size(
                min = 2,
                max = 48,
                message = "The address should be between 2 and 48 characters long"
        )
        String address
) {}