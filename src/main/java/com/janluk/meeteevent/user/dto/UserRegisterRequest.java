package com.janluk.meeteevent.user.dto;

public record UserRegisterRequest (
         String login,
         String password,
         String email,
         String phone,
         String city,
         String name,
         String surname
) {}
