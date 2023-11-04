package com.janluk.meeteevent.event.mapper;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.event.dto.EventDTO;
import com.janluk.meeteevent.event.dto.EventWithUsersDTO;
import com.janluk.meeteevent.event.mapper.annotations.TagsMapping;
import com.janluk.meeteevent.event.mapper.annotations.UserMapping;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = {OptionalUserMapper.class, OptionalTagsMapper.class})
public interface EventMapper {

    EventDTO toEventDTO(Event event);

    EventWithUsersDTO toEventWithUsersDTO(Event event);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "createdBy", qualifiedBy = UserMapping.class)
    @Mapping(target = "tags", qualifiedBy = TagsMapping.class)
    Event toEvent(EventCreateRequest event);

    @AfterMapping
    default void addUserToEvent(@MappingTarget Event event) {
        event.addUser(event.getCreatedBy());
    }

}
