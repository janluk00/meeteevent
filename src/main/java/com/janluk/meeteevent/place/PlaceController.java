package com.janluk.meeteevent.place;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/places")
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
