package com.janluk.meeteevent.event.mapper;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.event.dto.EventDTO;
import com.janluk.meeteevent.user.mapper.PasswordEncoderMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO toEventDTO (Event event);
}
