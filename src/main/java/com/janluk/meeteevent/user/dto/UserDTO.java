package com.janluk.meeteevent.user.dto;

import lombok.*;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String login;
    private String email;
    private String phone;
    private String city;
    private String name;
    private String surname;
}
