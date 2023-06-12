package com.janluk.meeteevent.user;

import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.fetchAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegisterRequest> registerUser(@RequestBody UserRegisterRequest newUser) {
        userService.createUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


}
