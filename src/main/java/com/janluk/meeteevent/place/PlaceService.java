package com.janluk.meeteevent.place;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> fetchAllPlaces() {
        return placeRepository.findAll();
    }

    public Place fetchPlaceById(UUID id) {
        return placeRepository.findByIdOrThrow(id);
    }
}
