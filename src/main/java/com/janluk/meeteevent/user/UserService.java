package com.janluk.meeteevent.user;

import com.janluk.meeteevent.event.Event;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import com.janluk.meeteevent.utils.exception.LoginAlreadyTaken;
import com.janluk.meeteevent.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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

    public User fetchUserById(UUID id){
        return userRepository.findByIdOrThrow(id);
    }

    public void createUser(UserRegisterRequest user) {
        if (isLoginAlreadyTaken(user.login())){
            throw new LoginAlreadyTaken("Login: " + user.login() + "already taken!");
        }

        if (isEmailAlreadyTaken(user.email())){
            throw new LoginAlreadyTaken("E-mail: " + user.email() + "already taken!");
        }

        User saveUser = userMapper.toUser(user);
        saveUser.setRoles(Set.of(Role.USER));
        userRepository.save(saveUser);
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
