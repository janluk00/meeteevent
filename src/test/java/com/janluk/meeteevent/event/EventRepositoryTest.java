package com.janluk.meeteevent.event;

import com.janluk.meeteevent.config.TestBase;
import com.janluk.meeteevent.place.Place;
import com.janluk.meeteevent.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTest extends TestBase {

    @Autowired
    EventRepository eventRepository;

    private Event actualEvent;
    private Event expiredEvent;
    private User creator;
    private User participant;

    @BeforeEach
    void setUp() throws ParseException {
        // TODO: create entity factory instead of this

        Place place1 = Place.builder()
                .address("Test Address1")
                .name("Test name1")
                .build();

        Place place2 = Place.builder()
                .address("Test Address2")
                .name("Test name2")
                .build();

        creator = User.builder()
                .name("TestName")
                .surname("TestSurname")
                .login("testlogin")
                .password("testpassword")
                .email("testemail@gmail.com")
                .city("New York")
                .phone("+48123123123")
                .build();

        participant = User.builder()
                .name("TestName2")
                .surname("TestSurname2")
                .login("testlogin2")
                .password("testpassword2")
                .email("testemail2@gmail.com")
                .city("New York")
                .phone("+48123123124")
                .build();

        Set<User> users = new HashSet<>(Set.of(creator, participant));

        actualEvent = Event.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2050-01-01 20:30:00.333"))
                .description("Test Description")
                .name("Test name")
                .place(place1)
                .users(users)
                .createdBy(creator)
                .build();

        expiredEvent = Event.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2010-01-01 20:30:00.333"))
                .description("Test Description2")
                .name("Test name2")
                .place(place2)
                .users(users)
                .createdBy(creator)
                .build();

        Set<Event> events = new HashSet<>(Arrays.asList(actualEvent, expiredEvent));
        creator.setEvents(events);
        creator.setOwnedEvents(events);
        participant.setEvents(events);

        eventRepository.saveAllAndFlush(events);
    }

    @Test
    void findByUserIdShouldFindOne() {
        List<Event> events = eventRepository.findAllEventsByUserId(participant.getId());

        assertThat(events)
                .hasSize(1)
                .containsExactly(actualEvent);
    }

    @Test
    void findOwnedEventsByUserIdShouldFindTwo() {
        List<Event> events = eventRepository.findAllEventsCreatedByUserById(creator.getId());

        assertThat(events)
                .hasSize(2)
                .extracting(Event::getId)
                .containsExactly(expiredEvent.getId(), actualEvent.getId());
    }

    @Test
    void findUnassignedEventsByUserIdShouldFindZero() {
        List<Event> events = eventRepository.findAllUnassignedEventsByUserId(participant.getId());

        assertThat(events)
                .hasSize(0);
    }

    @Test
    void findFinishedEventsByUserIdShouldFindOne() {
        List<Event> events = eventRepository.findAllFinishedEventsByUserId(participant.getId());

        assertThat(events)
                .hasSize(1)
                .containsExactly(expiredEvent);
    }
}
