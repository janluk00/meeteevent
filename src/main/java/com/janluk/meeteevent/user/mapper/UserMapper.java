package com.janluk.meeteevent.user.mapper;

import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "ownedEvents", ignore = true)
    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser(UserRegisterRequest userRegisterRequest);

    List<UserDTO> toUserDtos(List<User> users);
}
