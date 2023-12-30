package com.janluk.meeteevent.place;

import com.janluk.meeteevent.place.dto.PlaceDTO;
import com.janluk.meeteevent.place.mapper.PlaceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {

    @Mock
    PlaceRepository placeRepository;

    @Mock
    PlaceMapper placeMapper;

    @InjectMocks
    PlaceService placeService;

    private Place place;
    private PlaceDTO placeDTO;
    private UUID placeId;

    @BeforeEach
    void setUp() {
        place = Place.builder()
                .name("name")
                .address("address")
                .build();

        placeId = place.getId();

        placeDTO = PlaceDTO.builder()
                .id(placeId)
                .address("address")
                .name("name")
                .build();
    }

    @Test
    void findByIdShouldFindOne() {
        when(placeRepository.fetchById(placeId)).thenReturn(Optional.of(place));

        Place result = placeService.fetchPlaceById(placeId);

        assertNotNull(result);
        assertEquals(place, result);
        verify(placeRepository).fetchById(placeId);
    }
}
