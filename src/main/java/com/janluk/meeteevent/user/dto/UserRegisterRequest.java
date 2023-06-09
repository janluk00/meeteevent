package com.janluk.meeteevent.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest (
        @NotBlank(message = "Login is required")
        @Size(
                min = 3,
                max = 32,
                message = "The login should be between 3 and 32 characters long")
        @Pattern(regexp = "^[A-Za-z0-9]+([_.]?[A-Za-z0-9]+)*$")
        String login,

        @NotBlank(message = "Password is required")
        @Size(

                min = 8,
                max = 32,
                message = "The password should be between 8 and 32 characters long"
        )
        String password,

        @NotBlank(message = "E-mail is required")
        @Email(message = "Invalid e-mail")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$")
        String phone,

        @Size(
                min = 2,
                max = 48,
                message = "The city should be between 2 and 48 characters long"
        )
        String city,

        @NotBlank(message = "Name is required")
        @Pattern(regexp = "^[\\w.\\-, ']*$")
        @Size(
                min = 2,
                max = 20,
                message = "The name should be between 8 and 32 characters long"
        )
        String name,

        @NotBlank(message = "Surname is required")
        @Pattern(regexp = "^[\\w.\\-, ']*$")
        @Size(
                min = 2,
                max = 20,
                message = "The name should be between 8 and 32 characters long"
        )
        String surname
) {}
