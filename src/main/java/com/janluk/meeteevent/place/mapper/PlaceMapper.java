package com.janluk.meeteevent.place.mapper;

import com.janluk.meeteevent.place.Place;
import com.janluk.meeteevent.place.dto.PlaceCreateRequest;
import com.janluk.meeteevent.place.dto.PlaceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    Place toPlace(PlaceCreateRequest place);

    PlaceDTO toPlaceDTO(Place place);
}
