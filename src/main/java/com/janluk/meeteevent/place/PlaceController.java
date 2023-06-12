package com.janluk.meeteevent.place;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/place")
public class PlaceController {

    public final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Place>> getAllPlaces() {
        List<Place> places = placeService.fetchAllPlaces();

        return ResponseEntity.status(HttpStatus.OK).body(places);
    }
}
