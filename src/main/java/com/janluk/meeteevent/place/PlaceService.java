package com.janluk.meeteevent.place;

import com.janluk.meeteevent.place.dto.PlaceCreateRequest;
import com.janluk.meeteevent.place.mapper.PlaceMapper;
import com.janluk.meeteevent.utils.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> fetchAllPlaces() {
        return placeRepository.findAll();
    }

    public Place fetchPlaceById(UUID id) {
        return placeRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFound("Place with id: " + id + " not found"));
    }
}
