package com.janluk.meeteevent.user.dto;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class UserRegisterRequest {
    private String login;
    private String password;
    private String email;
    private String phone;
    private String city;
    private String name;
    private String surname;

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }
}
