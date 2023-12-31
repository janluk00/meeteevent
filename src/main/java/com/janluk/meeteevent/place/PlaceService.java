package com.janluk.meeteevent.place;

import com.janluk.meeteevent.exception.ResourceNotFound;
import com.janluk.meeteevent.place.dto.PlaceDTO;
import com.janluk.meeteevent.place.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

    public List<PlaceDTO> fetchAllPlaces() {
        List<Place> places = placeRepository.findAll();

        return places.stream()
                .map(placeMapper::toPlaceDTO)
                .collect(Collectors.toList());
    }

    public Place fetchPlaceById(UUID id) {
        return placeRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFound("Place with id: " + id + " not found"));
    }
}
