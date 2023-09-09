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

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchAllUsers());
    }

    // test method
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchUserById(id));
    }


    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegisterRequest> registerUser(@Valid @RequestBody UserRegisterRequest newUser) {
        userService.createUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PostMapping(value = "/{id}/event/{event_id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> assignUserToEvent(@PathVariable UUID id, @PathVariable("event_id") UUID eventId) {
        userService.assignUserToEvent(id, eventId);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventId.toString());
    }


}
