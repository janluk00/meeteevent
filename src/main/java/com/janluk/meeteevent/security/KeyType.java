package com.janluk.meeteevent.security;

import lombok.Getter;

@Getter
public enum KeyType {
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private String type;

    KeyType(String type) {
        this.type = type;
    }
}
