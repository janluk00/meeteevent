package com.janluk.meeteevent.event;

import com.janluk.meeteevent.event.dto.EventCreateRequest;
import com.janluk.meeteevent.event.dto.EventWithUsersDTO;
import com.janluk.meeteevent.event.mapper.EventMapper;
import com.janluk.meeteevent.exception.ResourceNotFound;
import com.janluk.meeteevent.place.Place;
import com.janluk.meeteevent.place.dto.PlaceCreateRequest;
import com.janluk.meeteevent.place.dto.PlaceDTO;
import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private EventWithUsersDTO eventWithUsersDTO;
    private EventCreateRequest eventCreateRequest;
    private UUID eventId;

    @BeforeEach
    void setUp() throws ParseException {
        // TODO: create entity factory instead of this

        Place place = Place.builder()
                .address("Test Address1")
                .name("Test name1")
                .build();

        PlaceCreateRequest placeCreateRequest = PlaceCreateRequest.builder()
                .address("Test Address1")
                .name("Test name1")
                .build();

        PlaceDTO placeDto = PlaceDTO.builder()
                .id(place.getId())
                .address("Test Address1")
                .name("Test name1")
                .build();

        UserDTO userDto = UserDTO.builder()
                .name("TestName")
                .surname("TestSurname")
                .login("testlogin")
                .email("testlogin123@gmail.com")
                .city("New York")
                .phone("+48123123123")
                .build();

        User user = User.builder()
                .name("TestName")
                .surname("TestSurname")
                .password("testpassword")
                .login("testlogin")
                .email("testlogin123@gmail.com")
                .city("New York")
                .phone("+48123123123")
                .build();

        event = Event.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2050-01-01 20:30:00.333"))
                .description("Test Description")
                .name("Test name")
                .place(place)
                .users(Set.of(user))
                .createdBy(user)
                .build();

        eventWithUsersDTO = EventWithUsersDTO.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2050-01-01 20:30:00.333"))
                .description("Test Description")
                .name("Test name")
                .place(placeDto)
                .users(List.of(userDto))
                .createdBy(userDto)
                .build();

        eventCreateRequest = EventCreateRequest.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2050-01-01 20:30:00.333"))
                .description("Test Description")
                .name("Test name")
                .place(placeCreateRequest)
                .createdBy(user.getId())
                .tags(List.of())
                .build();

        user.setOwnedEvents(Set.of(event));
        user.setEvents(Set.of(event));

        eventId = event.getId();
    }

    @Test
    void findEventByIdShouldReturnEventDTO() {
        when(eventRepository.fetchById(eventId)).thenReturn(Optional.of(event));
        when(eventMapper.toEventWithUsersDTO(event)).thenReturn(eventWithUsersDTO);

        EventWithUsersDTO result = eventService.fetchEventById(eventId);

        assertNotNull(result);
        assertEquals(eventWithUsersDTO, result);
        verify(eventRepository).fetchById(eventId);
        verify(eventMapper).toEventWithUsersDTO(event);
    }

    @Test
    void findUserByIdShouldThrowException() {
        UUID nonExistingUserId = UUID.randomUUID();

        when(eventRepository.fetchById(nonExistingUserId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> {
            eventService.fetchEventById(nonExistingUserId);
        });
    }

    @Test
    void createEventShouldSaveEvent() {
        when(eventMapper.toEvent(eventCreateRequest)).thenReturn(event);

        eventService.createEvent(eventCreateRequest);

        verify(eventRepository).save(any(Event.class));
    }
}
