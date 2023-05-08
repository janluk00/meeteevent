package com.janluk.meeteevent.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    public User(String login, String email, String password, String phone, String city, String name, String surname) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.name = name;
        this.surname = surname;
    }
}