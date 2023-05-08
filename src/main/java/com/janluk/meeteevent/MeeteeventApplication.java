package com.janluk.meeteevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MeeteeventApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeeteeventApplication.class, args);
	}

}
