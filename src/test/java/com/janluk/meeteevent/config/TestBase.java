package com.janluk.meeteevent.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;


public abstract class TestBase {

    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.9")
            .withReuse(true);

    static {
        postgres.start();
    }
}
