package com.janluk.meeteevent.user;

import com.janluk.meeteevent.mapper.EncodedMapping;
import com.janluk.meeteevent.mapper.PasswordEncoderMapper;
import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser (UserRegisterRequest userRegisterRequest);
}
