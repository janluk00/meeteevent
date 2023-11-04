package com.janluk.meeteevent.user;

import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.fetchAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        UserDTO user = userService.fetchUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(value = "/event/{event_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsersByEventId(@PathVariable("event_id") UUID eventId) {
        List<UserDTO> users = userService.fetchAllUsersByEventId(eventId);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegisterRequest> registerUser(@Valid @RequestBody UserRegisterRequest newUser) {
        userService.createUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping(value = "/{id}/assign/event/{event_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> assignUserToEvent(@PathVariable UUID id, @PathVariable("event_id") UUID eventId) {
        userService.assignUserToEvent(id, eventId);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventId.toString());
    }

    @DeleteMapping(value = "/{id}/event/{event_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unsubscribeUserFromEvent(@PathVariable UUID id, @PathVariable("event_id") UUID eventId) {
        userService.unsubscribeUserFromEvent(id, eventId);
    }

}
