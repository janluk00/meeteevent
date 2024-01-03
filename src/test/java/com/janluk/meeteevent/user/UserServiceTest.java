package com.janluk.meeteevent.user;

import com.janluk.meeteevent.event.EventRepository;
import com.janluk.meeteevent.exception.EmailAlreadyTaken;
import com.janluk.meeteevent.exception.LoginAlreadyTaken;
import com.janluk.meeteevent.exception.ResourceNotFound;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import com.janluk.meeteevent.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UUID userId;
    private User user;
    private UserDTO userDTO;
    private UserRegisterRequest userRegisterRequest;

    private final static String LOGIN = "testlogin";

    @BeforeEach
    void setup() {
        // TODO: create entity factory instead of this

        user = User.builder()
                .name("TestName")
                .surname("TestSurname")
                .login(LOGIN)
                .password("testpassword")
                .email("testlogin123@gmail.com")
                .city("New York")
                .phone("+48123123123")
                .build();

        userId = user.getId();

        userDTO = UserDTO.builder()
                .id(userId)
                .name("TestName")
                .surname("TestSurname")
                .login(LOGIN)
                .email("testlogin123@gmail.com")
                .city("New York")
                .phone("+48123123123")
                .build();

        userRegisterRequest = UserRegisterRequest.builder()
                .name("TestName")
                .surname("TestSurname")
                .login(LOGIN)
                .password("testpassword")
                .email("testlogin123@gmail.com")
                .city("New York")
                .phone("+48123123123")
                .build();
    }

    @Test
    void findUserByIdShouldReturnUserDTO() {
        when(userRepository.fetchById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDTO);

        UserDTO result = userService.fetchUserById(userId);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository).fetchById(userId);
        verify(userMapper).toUserDto(user);
    }

    @Test
    void findUserByIdShouldThrowException() {
        UUID nonExistingUserId = UUID.randomUUID();

        when(userRepository.fetchById(nonExistingUserId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> {
            userService.fetchUserById(nonExistingUserId);
        });
    }

    @Test
    void createUserShouldThrowEmailException() {
        when(userRepository.findUserByEmail(userRegisterRequest.email())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyTaken.class, () -> {
            userService.createUser(userRegisterRequest);
        });
    }

    @Test
    void createUserShouldThrowLoginException() {
        when(userRepository.findUserByLogin(userRegisterRequest.login())).thenReturn(Optional.of(user));

        assertThrows(LoginAlreadyTaken.class, () -> {
            userService.createUser(userRegisterRequest);
        });
    }

    @Test
    void createUserShouldSaveUser() {
        when(userRepository.findUserByEmail(userRegisterRequest.email())).thenReturn(Optional.empty());
        when(userRepository.findUserByLogin(userRegisterRequest.login())).thenReturn(Optional.empty());
        when(userMapper.toUser(userRegisterRequest)).thenReturn(user);

        userService.createUser(userRegisterRequest);

        verify(userRepository).save(any(User.class));
    }
}
