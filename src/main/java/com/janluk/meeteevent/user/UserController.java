package com.janluk.meeteevent.user;

import com.janluk.meeteevent.user.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    public final UserService userService;
    public final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.fetchAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByLogin(@PathVariable String login){
        Optional<User> optionalUser = userService.fetchUserByLogin(login);

        return optionalUser.map(
                user -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(modelMapper.map(user, UserDTO.class))
                )
                .orElse(ResponseEntity.notFound().build());
    }

}
