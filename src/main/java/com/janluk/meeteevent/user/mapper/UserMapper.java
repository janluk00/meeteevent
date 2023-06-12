package com.janluk.meeteevent.user.mapper;

import com.janluk.meeteevent.user.User;
import com.janluk.meeteevent.user.dto.UserDTO;
import com.janluk.meeteevent.user.dto.UserRegisterRequest;
import com.janluk.meeteevent.user.mapper.EncodedMapping;
import com.janluk.meeteevent.user.mapper.PasswordEncoderMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {
    UserDTO toUserDto(User user);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toUser (UserRegisterRequest userRegisterRequest);
}
