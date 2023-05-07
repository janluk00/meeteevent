package com.janluk.meeteevent.user;

import com.janluk.meeteevent.user.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> fetchAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(Objects::nonNull)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<User> fetchUserByLogin(String login){
        return userRepository.findEmployeeByLogin(login);
    }
}
