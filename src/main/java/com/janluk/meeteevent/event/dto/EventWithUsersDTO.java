package com.janluk.meeteevent.event.dto;
import com.janluk.meeteevent.place.dto.PlaceDTO;
import com.janluk.meeteevent.user.dto.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventWithUsersDTO(

        UUID id,
        String name,
        Date date,
        String description,
        PlaceDTO place,
        List<UserDTO> users,
        UserDTO createdBy
) {}
