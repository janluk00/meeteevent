package com.janluk.meeteevent.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.event.dto.EventDTO;
import com.janluk.meeteevent.event.dto.EventWithUsersDTO;
import com.janluk.meeteevent.exception.ResourceNotFound;
import com.janluk.meeteevent.place.dto.PlaceCreateRequest;
import com.janluk.meeteevent.place.dto.PlaceDTO;
import com.janluk.meeteevent.user.UserService;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService eventService;

    private EventDTO eventDTO;
    private EventWithUsersDTO eventWithUsersDTO;
    private UUID EVENT_ID = UUID.randomUUID();
    private EventCreateRequest eventRequest;

    @BeforeEach
    void setUp() throws ParseException {
        // TODO: create entity factory instead of this

        UserDTO userDTO = UserDTO.builder()
                .id(UUID.randomUUID())
                .name("One")
                .surname("First")
                .login("first")
                .email("first@gmail.com")
                .city("New York")
                .phone("+48999777444")
                .build();

        PlaceDTO placeDto = PlaceDTO.builder()
                .id(UUID.randomUUID())
                .address("Test Address1")
                .name("Test name1")
                .build();

        PlaceCreateRequest placeCreateRequest = PlaceCreateRequest.builder()
                .address("Test Address1")
                .name("Test name1")
                .build();

        eventDTO = EventDTO.builder()
                .id(EVENT_ID)
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2050-01-01 20:30:00.333"))
                .name("name")
                .description("description")
                .createdBy(userDTO)
                .tags(Collections.emptyList())
                .place(placeDto)
                .build();

        eventWithUsersDTO = EventWithUsersDTO.builder()
                .id(EVENT_ID)
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2050-01-01 20:30:00.333"))
                .name("name")
                .description("description")
                .createdBy(userDTO)
                .tags(Collections.emptyList())
                .place(placeDto)
                .users(List.of(userDTO))
                .build();

        eventRequest = EventCreateRequest.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2060-01-01 20:30:00.333"))
                .name("name")
                .description("description")
                .createdBy(userDTO.id())
                .tags(Collections.emptyList())
                .place(placeCreateRequest)
                .build();

    }

    @Test
    @WithMockUser
    void findAllEventsShouldFindOne() throws Exception {
        when(eventService.fetchAllEvents()).thenReturn(List.of(eventDTO));

        mvc.perform(
                get("/api/v1/events/all")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser
    void findEventByIdShouldFindOne() throws Exception {
        when(eventService.fetchEventById(EVENT_ID)).thenReturn(eventWithUsersDTO);

        mvc.perform(
                        get("/api/v1/events/%s".formatted(EVENT_ID.toString()))
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(EVENT_ID.toString()));
    }

    @Test
    @WithMockUser
    void findEventByIdShouldThrow404() throws Exception {
        UUID invalidId = UUID.randomUUID();

        when(eventService.fetchEventById(invalidId)).thenThrow(new ResourceNotFound("Event not found!"));

        mvc.perform(
                        get("/api/v1/events/%s".formatted(invalidId.toString()))
                                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEventShouldNotAllowCreationForUnauthenticatedUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(eventRequest);

        mvc.perform(
                        post("/api/v1/events")
                                .contentType(APPLICATION_JSON)
                                .content(request)
                                .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(eventService, never()).createEvent(any(EventCreateRequest.class));
    }

    @Test
    @WithMockUser
    void createEventShouldAllowCreation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(eventRequest);

        when(eventService.createEvent(eventRequest)).thenReturn(UUID.randomUUID());

        mvc.perform(
                        post("/api/v1/events")
                                .contentType(APPLICATION_JSON)
                                .content(request)
                                .with(csrf()))
                .andExpect(status().isCreated());

        verify(eventService).createEvent(any(EventCreateRequest.class));
    }
}
