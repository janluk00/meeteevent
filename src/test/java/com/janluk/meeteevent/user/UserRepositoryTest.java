package com.janluk.meeteevent.user;

import com.janluk.meeteevent.config.TestBase;
import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends TestBase {

    @Autowired
    UserRepository userRepository;

    private Event event;
    private User firstUser;
    private User secondUser;

    private final static String EMAIL = "testemail@gmail.com";
    private final static String LOGIN = "testlogin";

    @BeforeEach
    void setUp() throws ParseException {
        // TODO: create entity factory instead of this

        Place place1 = Place.builder()
                .address("Test Address1")
                .name("Test name1")
                .build();

        firstUser = User.builder()
                .name("TestName")
                .surname("TestSurname")
                .login(LOGIN)
                .password("testpassword")
                .email(EMAIL)
                .city("New York")
                .phone("+48123123123")
                .build();

        secondUser = User.builder()
                .name("TestName2")
                .surname("TestSurname2")
                .login("testlogin2")
                .password("testpassword2")
                .email("testemail2@gmail.com")
                .city("New York")
                .phone("+48123123124")
                .build();

        Set<User> users = new HashSet<>(Set.of(firstUser, secondUser));

        event = Event.builder()
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2050-01-01 20:30:00.333"))
                .description("Test Description")
                .name("Test name")
                .place(place1)
                .users(users)
                .createdBy(firstUser)
                .build();

        Set<Event> eventSet = new HashSet<>(Set.of(event));

        firstUser.setEvents(eventSet);
        firstUser.setOwnedEvents(eventSet);
        secondUser.setEvents(eventSet);

        userRepository.saveAll(users);
    }

    @Test
    void findEventParticipantsByEventIdShouldFindTwo() {
        List<User> users = userRepository.findAllUsersByEventId(event.getId());

        assertThat(users)
                .hasSize(2)
                .extracting(User::getId)
                .containsExactly(firstUser.getId(), secondUser.getId());
    }

    @Test
    void findUserByLoginShouldFindOne() {
        Optional<User> result = userRepository.findUserByLogin(LOGIN);

        assertThat(result).isNotEmpty();
        assertEquals(result.get().getId(), firstUser.getId());
    }

    @Test
    void findUserByEmailShouldFindOne() {
        Optional<User> result = userRepository.findUserByEmail(EMAIL);

        assertThat(result).isNotEmpty();
        assertEquals(result.get().getId(), firstUser.getId());
    }
}
