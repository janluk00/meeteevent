package com.janluk.meeteevent.place;

import com.janluk.meeteevent.place.dto.PlaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlaceService placeService;

    private PlaceDTO first;
    private PlaceDTO second;

    @BeforeEach
    void setUp() {
        // TODO: create entity factory instead of this

        first = PlaceDTO.builder()
                .id(UUID.randomUUID())
                .name("Place")
                .address("Address")
                .build();

        second = PlaceDTO.builder()
                .id(UUID.randomUUID())
                .name("Second place")
                .address("second address")
                .build();
    }

    @Test
    @WithMockUser
    void findAllPlacesShouldFindTwo() throws Exception {
        when(placeService.fetchAllPlaces()).thenReturn(List.of(first, second));

        mvc.perform(
                        get("/api/v1/places/all")
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }
}
