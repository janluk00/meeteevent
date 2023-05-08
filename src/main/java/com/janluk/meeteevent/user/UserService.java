package com.janluk.meeteevent.user;

import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
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
        return userRepository.findUserByLogin(login);
    }

    public void createUser(UserRegisterRequest user){
        userRepository.save(
                new User(user.getLogin(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPhone(),
                        user.getCity()
                )
        );
    }

    public boolean isLoginAlreadyTaken(String login){
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        return optionalUser.isPresent();
    }

    public boolean isEmailAlreadyTaken(String email){
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        return optionalUser.isPresent();
    }
}
