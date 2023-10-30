package com.janluk.meeteevent.tag.mapper;

import com.janluk.meeteevent.tag.Tag;
import com.janluk.meeteevent.tag.dto.TagCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "events", ignore = true)
    Tag toTag (TagCreateRequest tag);

}
