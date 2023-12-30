package com.janluk.meeteevent.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janluk.meeteevent.MeeteeventApplication;
import com.janluk.meeteevent.config.SecurityConfig;
import com.janluk.meeteevent.exception.ResourceNotFound;
import com.janluk.meeteevent.security.RsaKeyProperties;
import com.janluk.meeteevent.security.details.JpaUserDetailService;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(value = UserController.class)
@Import(SecurityConfig.class)
@EnableConfigurationProperties(value = RsaKeyProperties.class)
@TestPropertySource({"classpath:keys/private-test.pem", "classpath:keys/public-test.pem"})
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JpaUserDetailService jpaUserDetailService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @MockBean
    private JwtEncoder jwtEncoder;

    @MockBean
    private RsaKeyProperties rsaKeyProperties;

    private UserDTO first;
    private final UUID FIRST_ID = UUID.randomUUID();
    private UserDTO second;
    private final UUID SECOND_ID = UUID.randomUUID();
    private UserRegisterRequest userRequest;

    @BeforeEach
    void setUp() {
        // TODO: create entity factory instead of this

        first = UserDTO.builder()
                .id(FIRST_ID)
                .name("One")
                .surname("First")
                .login("first")
                .email("first@gmail.com")
                .city("New York")
                .phone("+48999777444")
                .build();

        second = UserDTO.builder()
                .id(SECOND_ID)
                .name("Two")
                .surname("Second")
                .login("second")
                .email("second@gmail.com")
                .city("Los Angeles")
                .phone("+48888555222")
                .build();

        userRequest = UserRegisterRequest.builder()
                .login("dwashington")
                .password("dwashington")
                .name("Denzel")
                .surname("Washington")
                .city("Washington")
                .email("dwashington@gmail.com")
                .phone("+48123123123")
                .build();

    }

    @Test
    @WithMockUser
    void findAllUsersShouldFindTwo() throws Exception {
        when(userService.fetchAllUsers()).thenReturn(List.of(first, second));

        mvc.perform(
                get("/api/v1/users/all")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser
    void findUserByIdShouldFindOne() throws Exception {
        when(userService.fetchUserById(FIRST_ID)).thenReturn(first);

        mvc.perform(
                get("/api/v1/users/%s".formatted(FIRST_ID.toString()))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(FIRST_ID.toString()));
    }

    @Test
    @WithMockUser
    void findUserByIdShouldThrow404() throws Exception {
        UUID invalidId = UUID.randomUUID();

        when(userService.fetchUserById(invalidId)).thenThrow(new ResourceNotFound("User not found!"));

        mvc.perform(
                get("/api/v1/users/%s".formatted(invalidId.toString()))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUserShouldAllowCreationForUnauthenticatedUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(userRequest);

        mvc.perform(
                post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .with(csrf()))
                .andExpect(status().isCreated());

        verify(userService).createUser(any(UserRegisterRequest.class));
    }
}
