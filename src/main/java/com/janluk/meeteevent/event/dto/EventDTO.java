package com.janluk.meeteevent.event.dto;

import com.janluk.meeteevent.user.dto.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventDTO(

        UUID id,
        String name,
        Date date,
        String description
) {}
