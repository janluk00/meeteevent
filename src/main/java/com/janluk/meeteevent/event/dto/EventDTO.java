package com.janluk.meeteevent.event.dto;

import com.janluk.meeteevent.user.dto.UserDTO;

import java.util.Date;
import java.util.List;

public record EventDTO(
        String name,
        Date date,
        String description,
        List<UserDTO> users,
        UserDTO createdBy
) {}
