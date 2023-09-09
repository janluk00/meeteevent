package com.janluk.meeteevent.event.mapper;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.event.dto.EventDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = OptionalUserMapper.class)
public interface EventMapper {
    EventDTO toEventDTO(Event event);


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "createdBy", qualifiedBy = UserMapping.class)
    Event toEvent(EventCreateRequest event);

    @AfterMapping
    default void addUserToEvent(@MappingTarget Event event) {
        System.out.println("Zrobilem sie!");
        event.addUser(event.getCreatedBy());
    }

}
