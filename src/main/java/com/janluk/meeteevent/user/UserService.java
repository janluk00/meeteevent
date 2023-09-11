package com.janluk.meeteevent.user;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.event.EventRepository;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import com.janluk.meeteevent.utils.exception.EmailAlreadyTaken;
import com.janluk.meeteevent.utils.exception.LoginAlreadyTaken;
import com.janluk.meeteevent.user.mapper.UserMapper;
import com.janluk.meeteevent.utils.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EventRepository eventRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.eventRepository = eventRepository;
    }

    public List<UserDTO> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public Optional<User> fetchUserByLogin(String login){
        return userRepository.findUserByLogin(login);
    }

    public UserDTO fetchUserById(UUID id){
        User user = userRepository.fetchById(id)
                .orElseThrow(() -> new ResourceNotFound("User with id: " + id + " not found"));

        return userMapper.toUserDto(user);

    }

    public void createUser(UserRegisterRequest user) {
        if (isLoginAlreadyTaken(user.login())){
            throw new LoginAlreadyTaken("Login: " + user.login() + "already taken!");
        }

        if (isEmailAlreadyTaken(user.email())){
            throw new EmailAlreadyTaken("E-mail: " + user.email() + "already taken!");
        }

        User saveUser = userMapper.toUser(user);
        saveUser.setRoles(Set.of(Role.USER));

        userRepository.save(saveUser);
    }

    public List<UserDTO> fetchAllUsersByEventId(UUID eventId) {
        List<User> users = userRepository.findAllUsersByEventId(eventId);

        return userMapper.toUserDtos(users);
    }

    @Transactional
    public void assignUserToEvent(UUID userId, UUID eventId) {
        User user = userRepository.fetchById(userId)
                .orElseThrow(() -> new ResourceNotFound("User with id: " + userId + " not found"));

        // TODO: ADD EXCEPTION ALREADY ASSIGNED

        Event event = eventRepository.fetchById(eventId)
                .orElseThrow(() -> new ResourceNotFound("Event with id: " + eventId + " not found"));

        event.addUser(user);
        eventRepository.save(event);
    }

    public boolean isLoginAlreadyTaken(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        return optionalUser.isPresent();
    }

    public boolean isEmailAlreadyTaken(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        return optionalUser.isPresent();
    }
}
